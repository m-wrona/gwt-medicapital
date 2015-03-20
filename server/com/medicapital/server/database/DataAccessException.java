package com.medicapital.server.database;

import com.medicapital.common.dao.ServerException;

public class DataAccessException extends ServerException {

    private static final long serialVersionUID = 4369679038619849828L;

	public DataAccessException() {
	    //empty
    }

	public DataAccessException(String msg, Throwable throwable) {
	    super(msg, throwable);
    }

	public DataAccessException(String errorMessage) {
	    super(errorMessage);
    }

	public DataAccessException(Throwable throwable) {
	    super(throwable);
    }

}
