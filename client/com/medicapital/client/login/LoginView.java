package com.medicapital.client.login;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface represents login view methods.
 * 
 * @author michal
 * 
 */
public interface LoginView {

	void setLogin(String text);

	String getLogin();

	void setPassword(String text);

	String getPassword();

	/**
	 * Show message that login data are incorrect
	 * 
	 * @param visible
	 */
	void setWrongLoginDataMessageVisible(boolean visible);

	void setNoLoginPasswordMessageVisible(boolean visible);

	void setLoginHandlerEnabled(boolean enabled);

	void setRegisterHandlerEnabled(boolean enabled);

	void setForgotPasswordHandlerEnabled(boolean enabled);

	/**
	 * Get view as widget
	 * 
	 * @return
	 */
	Widget asWidget();

	void setVisible(boolean show);

}
