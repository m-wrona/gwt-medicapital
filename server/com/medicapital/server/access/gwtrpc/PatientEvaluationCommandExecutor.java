package com.medicapital.server.access.gwtrpc;

import java.util.Set;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCommand;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCountCommand;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.logic.PatientEvaluationFacade;

public class PatientEvaluationCommandExecutor extends CommandExecutor<PatientEvaluation> {

	private static final long serialVersionUID = 711604574643924164L;
	private PatientEvaluationFacade patientEvaluationFacade;

	@Override
	protected SelectCountCommandResp<PatientEvaluation> handleSelectCountCommand(final SelectCountCommand<PatientEvaluation> selectCountCommand) throws CommandExecutionException, ServerException {
		if (selectCountCommand instanceof SelectDoctorEvaluationCountCommand) {
			final SelectDoctorEvaluationCountCommand selectDoctorEvaluationCountCommand = (SelectDoctorEvaluationCountCommand) selectCountCommand;
			final int count = patientEvaluationFacade.getDoctorEvaluationsCount(selectDoctorEvaluationCountCommand.getDoctorId());
			return new SelectCountCommandResp<PatientEvaluation>(PatientEvaluation.class, count);
		}
		throw new CommandExecutionException("Unsuppored commnad: " + selectCountCommand.getClass());
	}

	@Override
	protected SelectCommandResp<PatientEvaluation> handleSelectCommand(final SelectCommand<PatientEvaluation> selectCommand) throws CommandExecutionException, ServerException {
		if (selectCommand instanceof SelectDoctorEvaluationCommand) {
			final SelectDoctorEvaluationCommand selectDoctorEvaluationCommand = (SelectDoctorEvaluationCommand) selectCommand;
			final Set<PatientEvaluation> evaluations = patientEvaluationFacade.getDoctorPatientEvaluations(selectDoctorEvaluationCommand.getDoctorId(), selectDoctorEvaluationCommand.getStartRow(), selectDoctorEvaluationCommand.getRowCount());
			return new SelectCommandResp<PatientEvaluation>(PatientEvaluation.class, evaluations);
		}
		throw new CommandExecutionException("Unsuppored commnad: " + selectCommand.getClass());
	}

	@Override
	protected Class<PatientEvaluation> getEntityClass() {
		return PatientEvaluation.class;
	}

	public void setPatientEvaluationFacade(PatientEvaluationFacade patientEvaluationFacade) {
		setFacade(patientEvaluationFacade);
		this.patientEvaluationFacade = patientEvaluationFacade;
	}
}
