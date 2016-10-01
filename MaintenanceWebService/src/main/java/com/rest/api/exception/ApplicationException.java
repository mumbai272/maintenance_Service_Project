package com.rest.api.exception;
/**
 * 
 * @author vinayaksm
 *
 */
public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5136002658865912584L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(final String message) {
		super(message);
		
	}

	public ApplicationException(final Throwable cause) {
		super(cause);
	
	}

	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
		
	}

	public ApplicationException(final String message,final Throwable cause,final  boolean enableSuppression,
		final	boolean writableStack) {
		super(message, cause, enableSuppression, writableStack);
		
	}

}
