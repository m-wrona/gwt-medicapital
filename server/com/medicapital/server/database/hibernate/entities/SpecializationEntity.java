package com.medicapital.server.database.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Specialization")
@Entity
public class SpecializationEntity {

	@Id
	private int specializationId;

	private String name;

	public int getSpecializationId() {
    	return specializationId;
    }

	public void setSpecializationId(int specializationId) {
    	this.specializationId = specializationId;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

}
