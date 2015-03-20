package com.medicapital.server.database.hibernate.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PatientVisit")
public class PatientVisitEntity {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int visitId;

	private String title;

	private String notes;

	@Column(name = "beginTime")
	private Date begin;

	@Column(name = "endTime")
	private Date end;
	
	private Date lockTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctorId")
	private DoctorEntity doctor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patientId")
	private UserEntity patient;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "localizationId")
	private LocalizationEntity localization;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="evaluationId")
	private PatientEvaluationEntity evaluationEntity;

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public UserEntity getPatient() {
		return patient;
	}

	public void setPatient(UserEntity user) {
		this.patient = user;
	}

	public LocalizationEntity getLocalization() {
		return localization;
	}

	public void setLocalization(LocalizationEntity localization) {
		this.localization = localization;
	}

	public void setEvaluationEntity(PatientEvaluationEntity evaluationEntity) {
		this.evaluationEntity = evaluationEntity;
	}

	public PatientEvaluationEntity getEvaluationEntity() {
		return evaluationEntity;
	}
	
	public void setLockTime(Date lockTime) {
	    this.lockTime = lockTime;
    }
	
	public Date getLockTime() {
	    return lockTime;
    }

}
