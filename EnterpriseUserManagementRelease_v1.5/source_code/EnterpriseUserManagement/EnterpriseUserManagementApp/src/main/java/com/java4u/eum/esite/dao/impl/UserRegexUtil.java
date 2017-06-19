package com.java4u.eum.esite.dao.impl;

public class UserRegexUtil {

	public static boolean isAlphaNumeric(String s) {
		char[] ch = s.toCharArray();
		if (ch.length >= 10) {
			String pattern = "^[a-zA-Z0-9]*$";
			if (s.matches(pattern)) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateUserName(String userName) {
		String pattern = "^[a-z0-9_-]{3,15}.$";
		if (userName.matches(pattern)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(validateUserName("mkyong34"));
		System.out.println(validateUserName("arunkumar.varada"));
	}
}
