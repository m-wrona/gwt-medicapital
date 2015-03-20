package com.medicapital.common.entities.search;

import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.User.Sex;

public class SearchUserCriteria extends SearchCriteria {

	private String firstName;
	private String lastName;
	private Sex sex;
	private Localization localization;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}

	public Localization getLocalization() {
		return localization;
	}

}
