package com.medicapital.server.database;

import java.util.Set;
import com.medicapital.common.entities.Specialization;

public interface DaoSpecialization extends DaoAccess {

	/**
	 * Get specialization count
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getSpiecializationCount() throws DataAccessException;

	/**
	 * Get all specialization
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	Set<Specialization> getSpecializations() throws DataAccessException;

}
