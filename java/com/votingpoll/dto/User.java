package com.votingpoll.dto;

import org.springframework.data.annotation.Transient;

public class User {
	@Transient private String cpf;
	@Transient String ableToVoteStatus;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getAbleToVoteStatus() {
		return ableToVoteStatus;
	}
	public void setAbleToVoteStatus(String ableToVoteStatus) {
		this.ableToVoteStatus = ableToVoteStatus;
	}
}
