package com.java4u.eum.esystem.dao.impl;

import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;

public class UserRegexUtil {

	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.CONTROLLER);
	
	public static boolean isAlphaNumeric(String s) {
		char[] ch = s.toCharArray();
		LOGGER.debug("UserRegexUtil", "isAlphaNumeric", "len"+false);
		if (ch.length >= 10) {
			String pattern = "^[a-zA-Z0-9]*$";
			if (s.matches(pattern)) {
				LOGGER.debug("UserRegexUtil", "isAlphaNumeric", "regex result::::::  "+true);
				return true;
			}
		}
		LOGGER.debug("UserRegexUtil", "isAlphaNumeric", "regex result::::::  "+false);
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
