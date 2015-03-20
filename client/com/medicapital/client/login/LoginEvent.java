package com.medicapital.client.login;

import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.entities.LoginData;

/**
 * Event informs about login actions
 * 
 * @author mwronski
 * 
 */
final public class LoginEvent extends ClientEvent {

	private final LoginData loginData;

	/**
	 * Create login event
	 * 
	 * @param sender
	 * @param loginData
	 */
	public LoginEvent(Object sender, LoginData loginData) {
		super(sender);
		this.loginData = loginData;
	}

	/**
	 * Create logout event
	 * 
	 * @param sender
	 */
	public LoginEvent(Object sender) {
		super(sender);
		this.loginData = null;
	}

	final public boolean isLoggedIn() {
		return loginData != null;
	}

	final public LoginData getLoginData() {
		return loginData;
	}
}
