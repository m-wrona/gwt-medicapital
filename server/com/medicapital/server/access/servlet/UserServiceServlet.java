package com.medicapital.server.access.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicapital.common.dao.entity.ServiceURL;
import com.medicapital.common.dao.entity.UserService;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.search.SearchResult;
import com.medicapital.server.logic.EntityFacade;
import com.medicapital.server.logic.UserFacade;

@Service(ServiceURL.USER_SERVICE)
public final class UserServiceServlet extends BasicServiceServlet<User> implements UserService {

	private UserFacade userFacade;

	@Override
	public SearchResult<User> searchUsers(String firstName, String lastName, UserRole userRole, int startRow, int rowCount) {
		SearchResult<User> searchResult = new SearchResult<User>();
		searchResult.setResults(userFacade.searchUsers(firstName, lastName, userRole, startRow, rowCount));
		searchResult.setTootalResultsCount(userFacade.searchUsersCount(firstName, lastName, userRole));
		return searchResult;
	}

	@Override
	public boolean isLoginFree(String login) {
		return userFacade.isLoginFree(login);
	}

	@Autowired(required = true)
	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	@Override
	protected EntityFacade<User> getEntityFacade() {
		return userFacade;
	}

}
