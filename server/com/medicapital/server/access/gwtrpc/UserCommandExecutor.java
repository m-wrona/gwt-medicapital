package com.medicapital.server.access.gwtrpc;

import static com.medicapital.common.util.Util.*;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.User;
import com.medicapital.common.util.Util;
import com.medicapital.server.logic.UserFacade;

public class UserCommandExecutor extends CommandExecutor<User> {

	private static final long serialVersionUID = 2367757517275941800L;
	private UserFacade userFacade;

	@Override
	protected SelectCountCommandResp<User> handleSelectCountCommand(final SelectCountCommand<User> selectCountCommand) throws CommandExecutionException, ServerException {
		if (!Util.isEmpty(selectCountCommand.getLogin())) {
			int usersCount = userFacade.isLoginFree(selectCountCommand.getLogin()) ? 0 : 1;
			return new SelectCountCommandResp<User>(User.class, usersCount);
		}
		return super.handleSelectCountCommand(selectCountCommand);
	}

	@Override
	protected SelectCommandResp<User> handleSelectCommand(final SelectCommand<User> selectCommand) throws CommandExecutionException, ServerException {
		SelectCommandResp<User> resp = super.handleSelectCommand(selectCommand);
		if (resp != null) {
			return resp;
		}
		resp = new SelectCommandResp<User>(User.class);
		if (!isEmpty(selectCommand.getLogin())) {
			User user = userFacade.get(selectCommand.getLogin());
			resp.add(user);
		}
		return resp;
	}

	final public void setUserFacade(UserFacade userFacade) {
		setFacade(userFacade);
		this.userFacade = userFacade;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}
