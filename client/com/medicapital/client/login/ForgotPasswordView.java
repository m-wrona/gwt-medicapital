package com.medicapital.client.login;

/**
 * View interface for users which want to send to them forgotten password
 * 
 * @author michal
 * 
 */
public interface ForgotPasswordView {

	String getLogin();

	String getEmail();

	/**
	 * Make send password handler enabled
	 * 
	 * @param enabled
	 */
	void setSendPasswordHandlerEnabled(boolean enabled);

	/**
	 * Show confirmation message that e-mail was sent
	 * 
	 * @param visible
	 */
	void setEMailSentMsgVisible(boolean visible);

	/**
	 * Show message that e-mail was not found
	 * 
	 * @param visible
	 */
	void setLoginEmailNotFoundMsgVisible(boolean visible);

	/**
	 * Show message that e-mail is not valid
	 * 
	 * @param visible
	 */
	void setLoginOrEmailNotValidMsgVisible(boolean visible);

	/**
	 * Show message that some problem has occurred on server side
	 * 
	 * @param visible
	 */
	void setServerErrorMsgVisible(boolean visible);

	/**
	 * Make view visible
	 * 
	 * @param visible
	 */
	void setVisible(boolean visible);

}
