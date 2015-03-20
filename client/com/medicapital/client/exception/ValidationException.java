package com.medicapital.client.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8092717839544427187L;

	public ValidationException() {
	
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(Throwable throwable) {
		super(throwable);
	}
	
	public ValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}