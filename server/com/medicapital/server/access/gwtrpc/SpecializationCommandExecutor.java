package com.medicapital.server.access.gwtrpc;

import java.util.Set;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Specialization;
import com.medicapital.server.logic.GenericDataFacade;

public class SpecializationCommandExecutor extends CommandExecutor<Specialization> {

	private static final long serialVersionUID = -5739570540699961340L;
	private GenericDataFacade genericDataFacade;

	@Override
	protected SelectCommandResp<Specialization> handleSelectCommand(SelectCommand<Specialization> selectCommand) throws CommandExecutionException, ServerException {
		final Set<Specialization> dbSpecializations = genericDataFacade.getSpecializations();
		return new SelectCommandResp<Specialization>(Specialization.class, dbSpecializations);
	}

	@Override
	protected SelectCountCommandResp<Specialization> handleSelectCountCommand(SelectCountCommand<Specialization> selectCountCommand) throws CommandExecutionException, ServerException {
		return new SelectCountCommandResp<Specialization>(Specialization.class, genericDataFacade.getSpiecializationCount());
	}
	
	@Override
	protected Class<Specialization> getEntityClass() {
	    return Specialization.class;
	}
	
	public void setGenericDataFacade(GenericDataFacade genericDataFacade) {
	    this.genericDataFacade = genericDataFacade;
    }

}
