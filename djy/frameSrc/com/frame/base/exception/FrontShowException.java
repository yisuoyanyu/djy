package com.frame.base.exception;

public class FrontShowException extends RuntimeException {

	private static final long serialVersionUID = -5772532863477473203L;
	
	public FrontShowException() {
	}

	public FrontShowException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrontShowException(String message) {
		super(message);
	}

	public FrontShowException(Throwable cause) {
		super(cause);
	}

}
