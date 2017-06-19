/**
 * 
 */
package com.java4u.commons.exceptions;

/**
 * @author Arun
 *
 */
public class AccountNameInUseException extends Exception {

	/**
	 * 
	 */
	public AccountNameInUseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AccountNameInUseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AccountNameInUseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public AccountNameInUseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public AccountNameInUseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
