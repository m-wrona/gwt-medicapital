package com.medicapital.server.database.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Region")
public class RegionEntity {

	@Id
	private int regionId;
	
	private String name;

	public int getRegionId() {
    	return regionId;
    }

	public void setRegionId(int regionId) {
    	this.regionId = regionId;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }
	
}
