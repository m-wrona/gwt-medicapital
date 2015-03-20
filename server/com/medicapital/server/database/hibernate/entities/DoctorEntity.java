package com.medicapital.server.database.hibernate.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Doctor")
public class DoctorEntity {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int doctorId;

	private float averageEvaluation;

	private String notes;

	private String workId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private UserEntity userEntity;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = SpecializationEntity.class, cascade = CascadeType.ALL)
	@JoinTable(name = "DoctorSpecialization", joinColumns = { @JoinColumn(name = "doctorId") }, inverseJoinColumns = { @JoinColumn(name = "specializationId") })
	private Set<SpecializationEntity> specializations;

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public float getAverageEvaluation() {
		return averageEvaluation;
	}

	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Set<SpecializationEntity> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(Set<SpecializationEntity> specializations) {
		this.specializations = specializations;
	}

}
