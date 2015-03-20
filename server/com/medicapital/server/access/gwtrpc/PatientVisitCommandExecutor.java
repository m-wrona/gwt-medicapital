package com.medicapital.server.access.gwtrpc;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Set;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.visit.LockVisitCommand;
import com.medicapital.common.commands.visit.BookVisitCommandResp;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCommand;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCountCommand;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.util.Util;
import com.medicapital.server.logic.PatientVisitFacade;

public class PatientVisitCommandExecutor extends CommandExecutor<PatientVisit> {

	private static final long serialVersionUID = -6479150098099622365L;
	private PatientVisitFacade patientVisitFacade;

	@Override
	protected CommandResp<PatientVisit> handleCommand(final CommandReq<PatientVisit> clientCommand) throws CommandExecutionException, ServerException {
		if (clientCommand instanceof LockVisitCommand) {
			return handleLockVisit((LockVisitCommand) clientCommand);
		}
		return super.handleCommand(clientCommand);
	}

	/**
	 * Book visit
	 * 
	 * @param bookVisitCommand
	 * @return
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	private BookVisitCommandResp handleLockVisit(final LockVisitCommand bookVisitCommand) throws CommandExecutionException, ServerException {
		tracer(this).debug("Handling lock visit command: " + bookVisitCommand);
		PatientVisit bookedVisit = patientVisitFacade.lockVisit(bookVisitCommand.getBookedVisitId(), bookVisitCommand.getPatientId(), bookVisitCommand.getDoctorId(), bookVisitCommand.getVisitStartTime(), bookVisitCommand.getVisitEndTime(), bookVisitCommand.getVisitTitle());
		return new BookVisitCommandResp(bookedVisit);
	}

	@Override
	protected SelectCountCommandResp<PatientVisit> handleSelectCountCommand(final SelectCountCommand<PatientVisit> selectCountCommand) throws CommandExecutionException, ServerException {
		if (selectCountCommand instanceof SelectDoctorPatientVisitsCountCommand) {
			SelectDoctorPatientVisitsCountCommand selectDoctorVisitsCount = (SelectDoctorPatientVisitsCountCommand) selectCountCommand;
			int visitCount = patientVisitFacade.getDoctorPatientVisitsCount(selectDoctorVisitsCount.getDoctorId());
			return new SelectCountCommandResp<PatientVisit>(PatientVisit.class, visitCount);
		} else if (!Util.isEmpty(selectCountCommand.getLogin())) {
			int visitCount = patientVisitFacade.getPatientVisitsCount(selectCountCommand.getLogin());
			return new SelectCountCommandResp<PatientVisit>(PatientVisit.class, visitCount);
		}
		return handleSelectCountCommand(selectCountCommand);
	}

	@Override
	protected SelectCommandResp<PatientVisit> handleSelectCommand(final SelectCommand<PatientVisit> selectCommand) throws CommandExecutionException, ServerException {
		SelectCommandResp<PatientVisit> resp = super.handleSelectCommand(selectCommand);
		if (resp != null) {
			return resp;
		}
		resp = new SelectCommandResp<PatientVisit>(PatientVisit.class);
		if (selectCommand instanceof SelectDoctorPatientVisitsCommand) {
			return handleSelectDoctorVisitCommand((SelectDoctorPatientVisitsCommand) selectCommand);
		} else if (!Util.isEmpty(selectCommand.getLogin())) {
			final Set<PatientVisit> visits = patientVisitFacade.getPatientVisits(selectCommand.getLogin(), selectCommand.getStartRow(), selectCommand.getRowCount());
			resp.addAll(visits);
		}
		return resp;
	}

	private SelectCommandResp<PatientVisit> handleSelectDoctorVisitCommand(final SelectDoctorPatientVisitsCommand command) throws CommandExecutionException, ServerException {
		final SelectCommandResp<PatientVisit> resp = new SelectCommandResp<PatientVisit>(PatientVisit.class);
		if (command.getDoctorId() > 0) {
			Set<PatientVisit> visits = null;
			if (command.getBeginDate() != null && command.getEndDate() != null) {
				visits = patientVisitFacade.getDoctorVisits(command.getDoctorId(), command.getBeginDate(), command.getEndDate());
			} else {
				visits = patientVisitFacade.getDoctorVisits(command.getDoctorId(), command.getStartRow(), command.getRowCount());
			}
			resp.addAll(visits);
		}
		return resp;
	}

	@Override
	protected Class<PatientVisit> getEntityClass() {
		return PatientVisit.class;
	}

	public void setPatientVisitFacade(PatientVisitFacade patientVisitFacade) {
		setFacade(patientVisitFacade);
		this.patientVisitFacade = patientVisitFacade;
	}

}
