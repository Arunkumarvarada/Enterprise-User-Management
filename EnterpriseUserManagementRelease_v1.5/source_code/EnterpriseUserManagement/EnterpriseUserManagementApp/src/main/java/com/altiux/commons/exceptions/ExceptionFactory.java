package com.altiux.commons.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ExceptionFactory {
	private static Map<Integer, Exception> map = new HashMap<Integer, Exception>();

	static {

		map.put(InvalidInputException.INVALID_USER,
				new InvalidInputException("INVALID USER", InvalidInputException.INVALID_USER));
		map.put(InvalidInputException.INSTANCE_ID_NOT_PROVIDED,
				new InvalidInputException("INSTANCE ID NOT PROVIDED", InvalidInputException.INSTANCE_ID_NOT_PROVIDED));
		map.put(InvalidInputException.USER_ALREADY_AVAIABLE,
				new InvalidInputException("USER ALREADY AVAIABLE", InvalidInputException.USER_ALREADY_AVAIABLE));
		map.put(InvalidInputException.REQUEST_BODY_IMPROPER,
				new InvalidInputException("REQUEST BODY IMPROPER", InvalidInputException.REQUEST_BODY_IMPROPER));
		map.put(InvalidInputException.PARKING_COMPANY_DOES_NOT_EXISTS,
				new InvalidInputException("PARKING COMPANY DOES NOT EXISTS", InvalidInputException.PARKING_COMPANY_DOES_NOT_EXISTS));
		
	}

	public static InvalidInputException getException(int type) {
		return (InvalidInputException) map.get(type);
	}
}
