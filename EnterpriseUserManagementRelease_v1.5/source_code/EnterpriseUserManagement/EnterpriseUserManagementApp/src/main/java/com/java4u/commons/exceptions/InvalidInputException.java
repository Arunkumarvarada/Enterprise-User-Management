package com.java4u.commons.exceptions;

/**
 * 
 * @author Arun
 *
 */
@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	private int type = -1;

	public static int INVALID_USER =1;
	
	public static int USER_ALREADY_AVAIABLE = 2;

	public static int PARKING_COMPANY_DOES_NOT_EXISTS =3;

	public static int WRONG_CUSTNAME =4;

	public static int INSTANCE_ID_NOT_PROVIDED =5;
	
	public static int ADMIN_DETAILS_MISSING=6;
	
	public static int REQUEST_BODY_IMPROPER=7;
	
	
	public InvalidInputException(String arg0, int type) {
		super(arg0);
		this.type=type;
	}

	public InvalidInputException(String arg0) {
		super(arg0);
	}
	
	public int getType() {
		return type;
	}

}
