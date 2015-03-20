package com.medicapital.server.database;

import java.util.Set;
import com.medicapital.common.entities.City;

/**
 * Interface represents access to City table.
 * 
 * @author michal
 * 
 */
public interface DaoCity extends DaoAccess {

	/**
	 * Get cities count
	 * 
	 * @return
	 * @throws ServerException
	 */
	int getCitiesCount() throws DataAccessException;

	/**
	 * Get all cities
	 * 
	 * @return
	 * @throws ServerException
	 */
	Set<City> getCities() throws DataAccessException;
}
