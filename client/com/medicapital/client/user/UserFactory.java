package com.medicapital.client.user;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.core.mvp.EditEntityPresenter;
import com.medicapital.client.core.mvp.EntityPresenter;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.User;
import com.medicapital.common.validation.UserValidator;
import com.medicapital.common.validation.UserValidatorView;

final public class UserFactory {

	private final EntityCommandFactory<User> commandFactory = new EntityCommandFactory<User>(User.class);

	public EntityPresenter<User> createUserPresenter(final UserView view) {
		UserViewBinder viewBinder = new UserViewBinder(view, null);
		return new EntityPresenter<User>(User.class, view, viewBinder, DaoFactory.getEventBus(), DaoFactory.getUserService());
	}

	public EditEntityPresenter<User> createEditUserPresenter(final EditUserView view, UserValidatorView validatorView) {
		UserViewBinder viewBinder = new UserViewBinder(view, view);
		UserValidator userValidator = new UserValidator();
		userValidator.setView(validatorView);
		EditEntityPresenter<User> editUserPresenter = new EditEntityPresenter<User>(User.class, view, viewBinder, DaoFactory.getEventBus(), DaoFactory.getUserService());
		editUserPresenter.setEntityValidator(userValidator);
		return editUserPresenter;
	}

	public CreateUserPresenter createCreateUserPresenter(final CreateUserView view, UserValidatorView validatorView) {
		UserViewBinder viewBinder = new UserViewBinder(view, view);
		UserValidator userValidator = new UserValidator();
		userValidator.setView(validatorView);
		CreateUserPresenter createUserPresenter = new CreateUserPresenter(view, viewBinder, DaoFactory.getEventBus(), DaoFactory.getUserService());
		createUserPresenter.setEntityValidator(userValidator);
		return createUserPresenter;
	}

	public SearchUserPresenter createSearchUserPresenter(SearchUserView view) {
		return new SearchUserPresenter(view, DaoFactory.getEventBus(), DaoFactory.getUserService());
	}

	public EntityCommandFactory<User> getCommandFactory() {
		return commandFactory;
	}

}
