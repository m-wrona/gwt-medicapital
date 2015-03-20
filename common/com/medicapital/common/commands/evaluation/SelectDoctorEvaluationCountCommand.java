package com.medicapital.common.commands.evaluation;

import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.entities.PatientEvaluation;

public class SelectDoctorEvaluationCountCommand extends SelectCountCommand<PatientEvaluation> {

	private int doctorId;

	/**
	 * Constructor for RPC communication
	 */
	protected SelectDoctorEvaluationCountCommand() {
		super();
	}

	public SelectDoctorEvaluationCountCommand(int doctorId) {
		super(PatientEvaluation.class);
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}
}
