package com.medicapital.common.commands.evaluation;

import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.PatientEvaluation;

public class SelectDoctorEvaluationCommand extends SelectCommand<PatientEvaluation> {

	private int doctorId;

	/**
	 * Constructor for RPC communication
	 */
	protected SelectDoctorEvaluationCommand() {
		super();
	}

	public SelectDoctorEvaluationCommand(int doctorId) {
		super(PatientEvaluation.class);
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}
}
