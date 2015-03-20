package com.medicapital.server.security;

import com.medicapital.common.dao.ServerException;

final public class SecurityException extends ServerException {

	private static final long serialVersionUID = -6173537282038609891L;

	SecurityException() {
		super();
	}

	SecurityException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	SecurityException(String errorMessage) {
		super(errorMessage);
	}

	SecurityException(Throwable throwable) {
		super(throwable);
	}

}
