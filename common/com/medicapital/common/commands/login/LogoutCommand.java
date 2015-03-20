package com.medicapital.common.commands.login;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.LoginData;

public class LogoutCommand extends CommandReq<LoginData> {

	private String login;

	/**
	 * GWT-RPC communication
	 */
	protected LogoutCommand() {
		super();
	}

	public LogoutCommand(String login) {
		super(LoginData.class);
		this.login = login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
	
	@Override
	public String toString() {
		return "[" + super.toString() + ", login=" + login + "]";
	}
}
