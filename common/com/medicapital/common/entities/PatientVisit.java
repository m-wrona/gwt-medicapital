package com.medicapital.common.entities;

import java.util.Date;

/**
 * The persistent class for the PatientVisit database table.
 * 
 */
public class PatientVisit extends CalendarEvent {

	private Doctor doctor;

	private User patient;

	private PatientEvaluation evaluation;

	private Localization localization;

	/**
	 * Booking time is a reservation time stamp. After some time visit which
	 * wasn't confirmed(time stamp set to null) will be deleted.
	 */
	private Date lockTime;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(final Doctor doctor) {
		this.doctor = doctor;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(final User user) {
		patient = user;
	}

	public void setEvaluation(final PatientEvaluation evaluation) {
		this.evaluation = evaluation;
	}

	public PatientEvaluation getEvaluation() {
		return evaluation;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(final Localization localization) {
		this.localization = localization;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Date getLockTime() {
		return lockTime;
	}

	@Override
	protected void cloneData(CalendarEvent source, final CalendarEvent target) {
		super.cloneData(source, target);
		if (target instanceof PatientVisit && source instanceof PatientVisit) {
			final PatientVisit sourceVisit = (PatientVisit) source;
			final PatientVisit cloneVisit = (PatientVisit) target;
			cloneVisit.doctor = sourceVisit.doctor;
			cloneVisit.evaluation = sourceVisit.evaluation;
			cloneVisit.localization = sourceVisit.localization;
			cloneVisit.patient = sourceVisit.patient;
		}
	}

	public void rewriteData(PatientVisit patientVisit) {
		cloneData(patientVisit, this);
	}

	@Override
	public PatientVisit cloneEvent() {
		final PatientVisit clone = new PatientVisit();
		cloneData(this, clone);
		return clone;
	}

	@Override
	public String toString() {
		final StringBuilder data = new StringBuilder("[");
		data.append(super.toString());
		data.append(", user: " + patient);
		data.append(", doctor: " + doctor);
		data.append(", evaluation: " + evaluation);
		data.append(", localization: " + localization);
		data.append(", bookingTime: " + lockTime);
		data.append("]");
		return data.toString();
	}

}