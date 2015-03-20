package com.medicapital.server.database.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "City")
public class CityEntity {

	@Id
	private int cityId;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "regionId")
	private RegionEntity region;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegion(RegionEntity region) {
		this.region = region;
	}

	public RegionEntity getRegion() {
		return region;
	}

}
