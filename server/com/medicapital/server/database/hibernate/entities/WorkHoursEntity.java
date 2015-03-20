package com.medicapital.server.database.hibernate.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DoctorWorkHours")
public class WorkHoursEntity {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int workHoursId;

	private int startHour;

	private int startMinutes;

	private int endHour;

	private int endMinutes;

	private String day;

	private Date dateFrom;

	private Date dateTo;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "localizationId")
	private LocalizationEntity localization;

	private int doctorId;

	public int getWorkHoursId() {
		return workHoursId;
	}

	public void setWorkHoursId(int workHourId) {
		this.workHoursId = workHourId;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(int startMinutes) {
		this.startMinutes = startMinutes;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(int endMinutes) {
		this.endMinutes = endMinutes;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalizationEntity getLocalization() {
		return localization;
	}

	public void setLocalization(LocalizationEntity localization) {
		this.localization = localization;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

}
