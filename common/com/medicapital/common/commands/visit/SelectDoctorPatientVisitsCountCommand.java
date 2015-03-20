package com.medicapital.common.commands.visit;

import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.entities.PatientVisit;

public class SelectDoctorPatientVisitsCountCommand extends SelectCountCommand<PatientVisit> {

	private int doctorId;

	public SelectDoctorPatientVisitsCountCommand() {
		super(PatientVisit.class);
	}

	public SelectDoctorPatientVisitsCountCommand(int doctorId) {
		super(PatientVisit.class);
		this.doctorId = doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}
}
