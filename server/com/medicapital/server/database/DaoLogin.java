package com.medicapital.server.database;

import java.util.Date;
import com.medicapital.common.entities.LoginData;

/**
 * Interface enables access to login data
 * 
 * @author michal
 * 
 */
public interface DaoLogin extends DaoAccess {

	/**
	 * Login user to the service
	 * 
	 * @param login
	 * @param password
	 * @return null if user doesn't exist. Instance of LoginData when user can
	 *         be logged in into the service.
	 * @throws DataAccessException
	 */
	LoginData loggin(String login, char[] password) throws DataAccessException;

	/**
	 * Change password for user with following e-mail
	 * 
	 * @param login
	 * @param eMail
	 * @param password
	 * @throws DataAccessException
	 */
	boolean changePassword(String login, String eMail, char[] password) throws DataAccessException;

	/**
	 * Set last login date for user
	 * 
	 * @param login
	 * @param date
	 * @throws DataAccessException
	 */
	void setLastLoginDate(String login, Date date) throws DataAccessException;
}
