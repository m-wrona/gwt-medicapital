package com.medicapital.server.access.gwtrpc;

import java.util.Set;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Hobby;
import com.medicapital.server.logic.GenericDataFacade;

public class HobbyCommandExecutor extends CommandExecutor<Hobby> {

	private static final long serialVersionUID = -3313731330173198973L;
	private GenericDataFacade genericDataFacade;

	@Override
	protected SelectCommandResp<Hobby> handleSelectCommand(SelectCommand<Hobby> selectCommand) throws CommandExecutionException, ServerException {
		final Set<Hobby> dbHobbies = genericDataFacade.getHobbies();
		return new SelectCommandResp<Hobby>(Hobby.class, dbHobbies);
	}

	@Override
	protected SelectCountCommandResp<Hobby> handleSelectCountCommand(SelectCountCommand<Hobby> selectCountCommand) throws CommandExecutionException, ServerException {
		return new SelectCountCommandResp<Hobby>(Hobby.class, genericDataFacade.getHobbiesCount());
	}

	@Override
	protected Class<Hobby> getEntityClass() {
		return Hobby.class;
	}

	public void setGenericDataFacade(GenericDataFacade genericDataFacade) {
		this.genericDataFacade = genericDataFacade;
	}

}
