package com.medicapital.server.database;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medicapital.common.entities.User;

/**
 * Interface enables to perform operations on {@link User}.
 * 
 * @author michal
 * 
 */
@Component
public interface DaoUser extends DaoEntityAccess<User> {

	List<User> searchUsers(User searchCriteria, int startRow, int maxRows) throws DataAccessException;

	int searchUsersCount(User searchCriteria) throws DataAccessException;

	/**
	 * Get users count
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getCount() throws DataAccessException;

	/**
	 * Get user
	 * 
	 * @param login
	 * @return
	 * @throws DataAccessException
	 */
	User get(String login) throws DataAccessException;

	/**
	 * Delete user
	 * 
	 * @param login
	 * @throws DataAccessException
	 */
	void delete(String login) throws DataAccessException;

}
