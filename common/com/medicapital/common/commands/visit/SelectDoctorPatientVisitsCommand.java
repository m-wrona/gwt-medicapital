package com.medicapital.common.commands.visit;

import java.util.Date;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.PatientVisit;

public class SelectDoctorPatientVisitsCommand extends SelectCommand<PatientVisit> {

	private Date beginDate;
	private Date endDate;
	private int doctorId;

	public SelectDoctorPatientVisitsCommand() {
		super(PatientVisit.class);
	}

	public SelectDoctorPatientVisitsCommand(int doctorId) {
		super(PatientVisit.class);
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
