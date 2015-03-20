package com.medicapital.common.commands.login;

import java.util.Date;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.LoginData;

public class LoginCommandResp extends CommandResp<LoginData> {

	private LoginData loginData;
	private String sessionId;
	private Date sessionExpiryDate;

	/**
	 * Constructor for RPC communication
	 */
	protected LoginCommandResp() {
		super();
	}

	public LoginCommandResp(final String sessionId, final LoginData loginData, final Date sessionExpiryDate) {
		super(LoginData.class);
		this.sessionId = sessionId;
		this.sessionExpiryDate = sessionExpiryDate;
		this.loginData = loginData;
	}

	public LoginData getLoginData() {
		return loginData;
	}

	public Date getSessionExpiryDate() {
		return sessionExpiryDate;
	}

	public String getSessionId() {
		return sessionId;
	}

}
