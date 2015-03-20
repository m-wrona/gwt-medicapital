package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.search.SearchResult;

@RemoteServiceRelativePath(ServiceURL.BASE_URL + ServiceURL.USER_SERVICE)
public interface UserService extends BasicEntityService<User> {

	SearchResult<User> searchUsers(String firstName, String lastName, UserRole userRole, int startRow, int rowCount);
	
	/**
	 * Check whether login is not used yet
	 * 
	 * @param login
	 * @return
	 */
	boolean isLoginFree(String login);
}
