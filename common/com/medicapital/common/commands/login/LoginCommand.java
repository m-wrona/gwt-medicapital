package com.medicapital.common.commands.login;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.LoginData;

public class LoginCommand extends CommandReq<LoginData> {

	private String login;
	private char[] password;

	public LoginCommand(String login, String password) {
		super(LoginData.class);
		this.login = login;
		this.password = password.toCharArray();
	}

	/**
	 * Constructor for RPC communication
	 */
	protected LoginCommand() {

	}

	public String getLogin() {
		return login;
	}

	public char[] getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + ", login=" + login + "]";
	}

}
