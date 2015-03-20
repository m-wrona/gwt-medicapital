package com.medicapital.common.commands.visit;

import java.util.Date;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.PatientVisit;

/**
 * Command enables to make reservation for visits
 * 
 * @author mwronski
 * 
 */
public class LockVisitCommand extends CommandReq<PatientVisit> {

	/**
	 * If booked visit is set it means booking data should be updated
	 */
	private int bookedVisitId;
	private int patientId;
	private int doctorId;
	private Date visitStartTime;
	private Date visitEndTime;
	private String visitTitle;

	public LockVisitCommand() {
		super(PatientVisit.class);
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public Date getVisitStartTime() {
		return visitStartTime;
	}

	public void setVisitStartTime(Date visitStartTime) {
		this.visitStartTime = visitStartTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setBookedVisitId(int bookedVisitId) {
		this.bookedVisitId = bookedVisitId;
	}

	public int getBookedVisitId() {
		return bookedVisitId;
	}
	
	public void setVisitTitle(String visitTitle) {
	    this.visitTitle = visitTitle;
    }
	
	public String getVisitTitle() {
	    return visitTitle;
    }
	
	@Override
	public String toString() {
	    StringBuilder string = new StringBuilder("[ " + getClass().getName());
	    string.append(", bookedVisitId: ").append(bookedVisitId);
	    string.append(", patientId: ").append(patientId);
	    string.append(", doctorId: ").append(doctorId);
	    string.append(", visitStartTime: ").append(visitStartTime);
	    string.append(", visitEndTime: ").append(visitEndTime);
	    string.append(']');
	    return string.toString();
	}

}
