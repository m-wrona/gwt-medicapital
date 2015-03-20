package com.medicapital.common.commands.calendar;

import java.util.Date;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.CalendarEvent;

public class SelectCalendarEventCommand extends SelectCommand<CalendarEvent> {

	private Date beginDate;
	private Date endDate;
	private int doctorId;
	private int patientId;
	private int ownerId;

	public SelectCalendarEventCommand() {
		super(CalendarEvent.class);
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

	public int getDoctorId() {
    	return doctorId;
    }

	public void setDoctorId(int doctorId) {
    	this.doctorId = doctorId;
    }

	public int getPatientId() {
    	return patientId;
    }

	public void setPatientId(int patientId) {
    	this.patientId = patientId;
    }

	public int getOwnerId() {
    	return ownerId;
    }

	public void setOwnerId(int ownerId) {
    	this.ownerId = ownerId;
    }
	
	@Override
	public String toString() {
	    return "[begin date: " + beginDate + ", end date: " + endDate + ", " +  super.toString() + "]";
	}
	
}
