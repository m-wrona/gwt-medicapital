package com.medicapital.server.database;

public interface DaoAccess {

	/**
	 * Clean stuff
	 * 
	 * @throws DataAccessException
	 */
	void clean() throws DataAccessException;

	/**
	 * Close access to data and clean stuff
	 * 
	 * @throws DataAccessException
	 */
	void close() throws DataAccessException;
}
