/**
 * 
 */
package com.altiux.commons.exceptions;

/**
 * @author Ankit.A
 *
 */
public class RequestBodyImproperException extends Exception {

	/**
	 * 
	 */
	public RequestBodyImproperException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RequestBodyImproperException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestBodyImproperException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public RequestBodyImproperException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public RequestBodyImproperException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
