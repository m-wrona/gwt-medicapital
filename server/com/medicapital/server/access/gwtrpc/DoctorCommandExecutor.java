package com.medicapital.server.access.gwtrpc;

import java.util.Set;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Doctor;
import com.medicapital.server.logic.DoctorFacade;

public class DoctorCommandExecutor extends CommandExecutor<Doctor> {

	private static final long serialVersionUID = -8254740127530132567L;
	private DoctorFacade doctorFacade;

	@Override
	protected SelectCountCommandResp<Doctor> handleSelectCountCommand(final SelectCountCommand<Doctor> selectCountCommand) throws CommandExecutionException, ServerException {
		return super.handleSelectCountCommand(selectCountCommand);
	}

	@Override
	protected SelectCommandResp<Doctor> handleSelectCommand(final SelectCommand<Doctor> selectCommand) throws CommandExecutionException, ServerException {
		SelectCommandResp<Doctor> selectCommandResp = super.handleSelectCommand(selectCommand);
		if (selectCommandResp != null) {
			return selectCommandResp;
		}
		selectCommandResp = new SelectCommandResp<Doctor>(Doctor.class);
		if (selectCommand.getLogin() != null && !selectCommand.getLogin().isEmpty()) {
			final Doctor doctor = doctorFacade.get(selectCommand.getLogin());
			selectCommandResp.add(doctor);
		}
		return selectCommandResp;
	}

	final public void setDoctorFacade(DoctorFacade doctorFacade) {
		setFacade(doctorFacade);
		this.doctorFacade = doctorFacade;
	}

	@Override
	protected Class<Doctor> getEntityClass() {
		return Doctor.class;
	}

}
