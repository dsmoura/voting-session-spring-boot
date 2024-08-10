package com.votingpoll.utils;

public class CpfValidator {
	
	public static boolean isValidCPF(String cpf) {
		if (cpf == null || (cpf.length() != 11)) {
			return false;
		}
		if (cpf.equals("00000000000")
				|| cpf.equals("11111111111")
				|| cpf.equals("22222222222")
				|| cpf.equals("33333333333")
				|| cpf.equals("44444444444")
				|| cpf.equals("55555555555")
				|| cpf.equals("66666666666")
				|| cpf.equals("77777777777")
				|| cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {
			return (false);
		}
		char dig10, dig11;
		int sm, i, r, num, weight;
		try {
			sm = 0;
			weight = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * weight);
				weight = weight - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}
			sm = 0;
			weight = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * weight);
				weight = weight - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}
			return ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)));
		} catch (RuntimeException error) {
			return (false);
		}
	}
}