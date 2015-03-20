package com.medicapital.common.commands.login;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.LoginData;

public class ForgotPasswordCommand extends CommandReq<LoginData> {

	private static final long serialVersionUID = 1658257524644497459L;

	private String eMail;
	private String login;

	public ForgotPasswordCommand(String login, String eMail) {
		super(LoginData.class);
		this.eMail = eMail;
		this.login = login;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected ForgotPasswordCommand() {

	}

	public String getLogin() {
		return login;
	}

	public String geteMail() {
		return eMail;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + ", eMail=" + eMail + ", login=" + login + "]";
	}

}
