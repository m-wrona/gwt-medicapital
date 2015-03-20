package com.medicapital.server.logic;

import java.util.Set;
import com.medicapital.common.entities.City;
import com.medicapital.common.entities.Hobby;
import com.medicapital.common.entities.Specialization;
import com.medicapital.server.database.DaoCity;
import com.medicapital.server.database.DaoHobby;
import com.medicapital.server.database.DaoSpecialization;

public class GenericDataFacade {

	private DaoCity daoCity;
	private DaoHobby daoHobby;
	private DaoSpecialization daoSpecialization;

	public Set<City> getCities() {
		return daoCity.getCities();
	}

	public int getCitiesCount() {
		return daoCity.getCitiesCount();
	}

	public Set<Hobby> getHobbies() {
		return daoHobby.getHobbies();
	}

	public int getHobbiesCount() {
		return daoHobby.getHobbiesCount();
	}

	public Set<Specialization> getSpecializations() {
		return daoSpecialization.getSpecializations();
	}

	public int getSpiecializationCount() {
		return daoSpecialization.getSpiecializationCount();
	}

	public void setDaoCity(DaoCity daoCity) {
		this.daoCity = daoCity;
	}

	public void setDaoHobby(DaoHobby daoHobby) {
		this.daoHobby = daoHobby;
	}

	public void setDaoSpecialization(DaoSpecialization daoSpecialization) {
		this.daoSpecialization = daoSpecialization;
	}

}
