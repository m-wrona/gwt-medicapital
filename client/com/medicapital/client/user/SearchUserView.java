package com.medicapital.client.user;

import com.medicapital.client.core.mvp.SearchEntityPresenterView;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;

public interface SearchUserView extends SearchEntityPresenterView<User> {

	void display(int userId, String firstName, String lastName, UserRole userRole);

	String getSearchFirstName();

	String getSearchLastName();

}
