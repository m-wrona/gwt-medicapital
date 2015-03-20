package com.medicapital.server.database;

import java.util.Set;
import com.medicapital.common.entities.Hobby;

/**
 * Interface represents access to Hobby table.
 * 
 * @author michal
 * 
 */
public interface DaoHobby extends DaoAccess {

	/**
	 * Get hobbies count
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getHobbiesCount() throws DataAccessException;

	/**
	 * Get all hobies
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	Set<Hobby> getHobbies() throws DataAccessException;
}
