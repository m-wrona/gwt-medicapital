package com.medicapital.server.access.gwtrpc;

import com.medicapital.common.dao.ServerException;

public class CommandExecutionException extends ServerException {

	private static final long serialVersionUID = -817577828324819089L;

	public CommandExecutionException() {
		// empty
	}

	public CommandExecutionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommandExecutionException(String errorMessage) {
		super(errorMessage);
	}

	public CommandExecutionException(Throwable throwable) {
		super(throwable);
	}

}
