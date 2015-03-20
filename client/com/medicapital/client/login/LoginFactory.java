package com.medicapital.client.login;

import com.medicapital.client.dao.DaoFactory;

final public class LoginFactory {

	public LoginPresenter createLogin() {
		LoginForm loginForm = new LoginForm();
		LoggedUserForm loggedUserForm = new LoggedUserForm();
		LoginPresenter loginPresenter = new LoginPresenter(loginForm, loggedUserForm, DaoFactory.getEventBus(), DaoFactory.getServiceAccess(), SessionManager.getInstacne());
		loginForm.setLoginPresenter(loginPresenter);
		loginForm.setForgotPasswordView(createForgotPasswordView());
		loggedUserForm.setLoginPresenter(loginPresenter);
		return loginPresenter;
	}

	private ForgotPasswordForm createForgotPasswordView() {
		ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
		ForgotPasswordPresenter forgotPasswordPresenter = new ForgotPasswordPresenter(forgotPasswordForm, DaoFactory.getServiceAccess());
		forgotPasswordForm.setForgotPasswordPresenter(forgotPasswordPresenter);
		return forgotPasswordForm;
	}

}
