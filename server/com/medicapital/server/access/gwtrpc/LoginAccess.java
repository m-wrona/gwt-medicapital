package com.medicapital.server.access.gwtrpc;

import static com.medicapital.server.log.Tracer.tracer;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.login.ForgotPasswordCommand;
import com.medicapital.common.commands.login.ForgotPasswordCommandResp;
import com.medicapital.common.commands.login.LoginCommand;
import com.medicapital.common.commands.login.LoginCommandResp;
import com.medicapital.common.commands.login.LogoutCommand;
import com.medicapital.common.commands.login.LogoutCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.LoginData;
import com.medicapital.server.security.SessionData;

/**
 * Class enables to login in user to the service
 * 
 * @author michal
 * 
 */
public class LoginAccess extends CommandExecutor<LoginData> {

	private static final long serialVersionUID = -8317298141075715299L;

	@Override
	protected CommandResp<LoginData> handleCommand(final CommandReq<LoginData> clientCommand) throws CommandExecutionException, ServerException {
		tracer(this).debug("Exeucting login access command: " + clientCommand);
		if (clientCommand instanceof LoginCommand) {
			return handleLoginCommand((LoginCommand) clientCommand);
		} else if (clientCommand instanceof LogoutCommand) {
			return handleLogoutCommand((LogoutCommand) clientCommand);
		} else if (clientCommand instanceof ForgotPasswordCommand) {
			return handleForgotPasswordCommand((ForgotPasswordCommand) clientCommand);
		}
		throw new ServerException("Unknown login command: " + clientCommand);
	}

	private LogoutCommandResp handleLogoutCommand(final LogoutCommand logoutCommand) throws CommandExecutionException, ServerException {
		boolean logout = getSecurityManager().logout(logoutCommand.getLogin());
		return new LogoutCommandResp(logout);
	}

	/**
	 * Handle login command
	 * 
	 * @param loginCommand
	 * @return
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	private LoginCommandResp handleLoginCommand(final LoginCommand loginCommand) throws CommandExecutionException, ServerException {
		final SessionData sessionData = getSecurityManager().loggin(loginCommand.getLogin(), loginCommand.getPassword(), getHttpSession());
		if (sessionData != null) {
			setSessionData(sessionData);
			return new LoginCommandResp(sessionData.getSessionId(), sessionData.getLoginData(), sessionData.getExpiryDate());
		} else {
			return new LoginCommandResp(null, null, null);
		}
	}

	/**
	 * Handle forgotten password command
	 * 
	 * @param command
	 * @return
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	private ForgotPasswordCommandResp handleForgotPasswordCommand(final ForgotPasswordCommand command) throws CommandExecutionException, ServerException {
		boolean passwordSent = getSecurityManager().sendForgottenPassword(command.getLogin(), command.geteMail());
		return new ForgotPasswordCommandResp(passwordSent);
	}

	@Override
	protected Class<LoginData> getEntityClass() {
		return LoginData.class;
	}

}
