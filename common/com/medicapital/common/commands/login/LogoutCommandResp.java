package com.medicapital.common.commands.login;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.LoginData;

public class LogoutCommandResp extends CommandResp<LoginData> {

	private boolean logout;

	/**
	 * GWT-RPC communication
	 */
	protected LogoutCommandResp() {
		super();
	}

	public LogoutCommandResp(boolean logout) {
		super(LoginData.class);
		this.logout = logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}

	public boolean isLogout() {
		return logout;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + ", logout=" + logout + "]";
	}
}
