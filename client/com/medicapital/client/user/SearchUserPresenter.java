package com.medicapital.client.user;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.mvp.SearchEntityPresenter;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.common.dao.entity.UserServiceAsync;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.search.SearchResult;

public final class SearchUserPresenter extends SearchEntityPresenter<User> {

	private final SearchUserView view;
	private final UserServiceAsync userService;

	public SearchUserPresenter(SearchUserView view, EventBus eventBus, UserServiceAsync userService) {
		super(User.class, view, eventBus);
		this.view = view;
		this.userService = userService;
	}

	@Override
	protected void displayEntityOnView(User entity) {
		view.display(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUserRole());
	}

	@Override
	protected void getElements(int startRow, int elementCount) {
		view.setViewBlocked(true);
		userService.searchUsers(view.getSearchFirstName(), view.getSearchLastName(), null, startRow, elementCount, new ServerCallback<SearchResult<User>>(view) {
			@Override
			public void response(SearchResult<User> result) {
				display(result.getResults());
				setTotalRows(result.getTootalResultsCount());
			}
		});
	}

	public SearchUserView getSearchUserView() {
		return view;
	}

}
