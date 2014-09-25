package com.allchange.token;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Token {
	private static Token token;

	public static Token instance() {
		if (token == null) {
			token = new Token();
		}
		return token;
	}

	public String getToken() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(250, random).toString(32).toUpperCase();
	}
}
