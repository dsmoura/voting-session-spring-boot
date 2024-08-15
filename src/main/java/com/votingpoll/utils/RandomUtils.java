package com.votingpoll.utils;

public class RandomUtils {
	public static int getRandomNumber(int minRange, int maxRange) {
	    return (int) ((Math.random() * (maxRange - minRange)) + minRange);
	}
}
