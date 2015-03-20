package com.medicapital.server.database.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "Localization")
@Entity
public class LocalizationEntity {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int localizationId;
	private String address;
	private String postalCode;
	private int cityId;
	
	public int getLocalizationId() {
    	return localizationId;
    }
	public void setLocalizationId(int localizationId) {
    	this.localizationId = localizationId;
    }
	public String getAddress() {
    	return address;
    }
	public void setAddress(String address) {
    	this.address = address;
    }
	public String getPostalCode() {
    	return postalCode;
    }
	public void setPostalCode(String postalCode) {
    	this.postalCode = postalCode;
    }
	public int getCityId() {
    	return cityId;
    }
	public void setCityId(int cityId) {
    	this.cityId = cityId;
    }
	
}
