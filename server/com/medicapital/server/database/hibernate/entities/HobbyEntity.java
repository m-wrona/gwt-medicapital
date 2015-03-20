package com.medicapital.server.database.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Hobby")
public class HobbyEntity {

	@Id
	private int hobbyId;
	private String name;
	private String description;
	
	public int getHobbyId() {
    	return hobbyId;
    }
	public void setHobbyId(int hobbyId) {
    	this.hobbyId = hobbyId;
    }
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public String getDescription() {
    	return description;
    }
	public void setDescription(String description) {
    	this.description = description;
    }
	
	
	
}
