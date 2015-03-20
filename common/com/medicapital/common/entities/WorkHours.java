package com.medicapital.common.entities;

import java.util.Date;

/**
 * Entities represents doctor's work hours
 * 
 * @author mwronski
 * 
 */
public class WorkHours implements SerializableEntity {

	private int workHoursId;
	private int startHour;
	private int startMinutes;
	private int endHour;
	private int endMinutes;
	private Day day;
	private Date dateFrom;
	private Date dateTo;
	private String description;
	private int doctorId;
	private Localization localization;

	@Override
	public int getId() {
		return workHoursId;
	}

	@Override
	public void setId(int entityId) {
		workHoursId = entityId;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public void setStartMinutes(int startMinutes) {
		this.startMinutes = startMinutes;
	}

	public int getStartMinutes() {
		return startMinutes;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public void setEndMinutes(int endMinutes) {
		this.endMinutes = endMinutes;
	}

	public int getEndMinutes() {
		return endMinutes;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date date) {
		this.dateFrom = date;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}

	public Localization getLocalization() {
		return localization;
	}

	public boolean isSpecialEvent() {
		return day == null;
	}

	public boolean isFreeDay() {
		return startHour == 0 && startMinutes == 0 && endHour == 0 && endMinutes == 0;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append("workHoursId: ").append(workHoursId);
		string.append(", doctorId: ").append(doctorId);
		string.append(", day: ").append(day);
		string.append(", dateFrom: ").append(dateFrom);
		string.append(", dateTo: ").append(dateTo);
		string.append(", startHour: ").append(startHour);
		string.append(", startMinutes: ").append(startMinutes);
		string.append(", endHour: ").append(endHour);
		string.append(", endMinutes: ").append(endMinutes);
		string.append(", description: ").append(description);
		string.append(", localization: ").append(localization);
		string.append("]");
		return string.toString();
	}

}
