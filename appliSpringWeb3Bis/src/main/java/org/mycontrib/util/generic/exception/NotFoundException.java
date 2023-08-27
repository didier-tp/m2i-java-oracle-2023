package org.mycontrib.util.generic.exception;


public class NotFoundException extends RuntimeException {
	
	//private String detail;

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
