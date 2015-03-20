package com.medicapital.common.commands.doctor.work;

import java.util.Date;

import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.WorkHours;

public class SelectWorkHoursCommand extends SelectCommand<WorkHours> {

	private int doctorId;
	private Date dateFrom;
	private Date dateTo;

	public SelectWorkHoursCommand() {
		super(WorkHours.class);
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append("doctorId: ").append(doctorId);
		string.append(", dateFrom: ").append(dateFrom);
		string.append(", dateTo: ").append(dateTo);
		string.append(",").append(super.toString()).append("]");
		return string.toString();
	}

}
