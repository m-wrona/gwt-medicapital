package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.search.SearchResult;

public interface UserServiceAsync extends BasicEntityServiceAsync<User> {

	void isLoginFree(String login, AsyncCallback<Boolean> callback);

	void searchUsers(String firstName, String lastName, UserRole userRole, int startRow, int rowCount, AsyncCallback<SearchResult<User>> callback);


}
