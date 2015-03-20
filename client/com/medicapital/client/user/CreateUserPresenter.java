package com.medicapital.client.user;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.mvp.CreateEntityPresenter;
import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.common.dao.entity.UserServiceAsync;
import com.medicapital.common.entities.User;

/**
 * Presenter allows to create new user
 * 
 * @author michal
 * 
 */
public final class CreateUserPresenter extends CreateEntityPresenter<User> {

	private final CreateUserView view;
	private final UserServiceAsync entityService;

	public CreateUserPresenter(CreateUserView view, EntityViewBinder<User> viewBinder, EventBus eventBus, UserServiceAsync entityService) {
		super(User.class, view, viewBinder, eventBus, entityService);
		this.view = view;
		this.entityService = entityService;
		initHandlers();
	}

	private void initHandlers() {
		view.getIsLoginFreeClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				checkIsLoginFree();
			}
		});
	}

	/**
	 * Check if given login is not used in service yet.
	 * 
	 */
	public void checkIsLoginFree() {
		final String login = view.getLogin();
		tracer(CreateUserPresenter.class).debug("Checking if login is free, login: " + login);
		view.setLoginFreeMessageVisible(false);
		view.setLoginExistMessageVisible(false);
		view.setLoginEmptyMessageVisible(false);
		if (login == null || login.isEmpty()) {
			view.setLoginEmptyMessageVisible(true);
			return;
		}
		entityService.isLoginFree(login, new ServerCallback<Boolean>() {
			@Override
			public void response(Boolean loginFree) {
				if (loginFree) {
					view.setLoginFreeMessageVisible(true);
				} else {
					view.setLoginExistMessageVisible(true);
				}
			}
		});
	}

}
