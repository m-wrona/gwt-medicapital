package com.medicapital.client.exception;

public class UnsupportedOperationException extends RuntimeException {

	private static final long serialVersionUID = 5525457875885875441L;

	public UnsupportedOperationException() {

	}

	public UnsupportedOperationException(String msg) {
		super(msg);
	}

	public UnsupportedOperationException(Throwable throwable) {
		super(throwable);
	}

	public UnsupportedOperationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
