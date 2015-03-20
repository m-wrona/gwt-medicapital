package com.medicapital.server.access.gwtrpc;

import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.VisitType;
import com.medicapital.server.logic.VisitTypeFacade;

public class VisitTypeCommandExecutor extends CommandExecutor<VisitType> {

	private static final long serialVersionUID = 4902405940365979473L;
	private VisitTypeFacade visitTypeFacade;

	@Override
	protected SelectCommandResp<VisitType> handleSelectCommand(SelectCommand<VisitType> selectCommand) throws CommandExecutionException, ServerException {
		if (selectCommand.getUserId() > 0) {
			SelectCommandResp<VisitType> resp = new SelectCommandResp<VisitType>(VisitType.class);
			resp.addAll(visitTypeFacade.getDoctorVisitTypes(selectCommand.getUserId()));
			return resp;
		}
		return super.handleSelectCommand(selectCommand);
	}

	@Override
	protected Class<VisitType> getEntityClass() {
		return VisitType.class;
	}

	public void setVisitTypeFacade(VisitTypeFacade visitTypeFacade) {
		this.visitTypeFacade = visitTypeFacade;
		setFacade(visitTypeFacade);
	}
}
