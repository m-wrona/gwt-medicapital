package com.medicapital.client.doctor;

import com.medicapital.common.entities.User.Sex;

public interface SearchDoctorCriteriaView {

	void setFirstName(String text);

	String getFirstName();

	void setLastName(String text);

	String getLastName();

	void setAddress(String text);

	String getAddress();

	void setCityId(int cityId);

	int getCityId();

	void setSex(Sex sex);

	Sex getSex();

	void setEvaluationFrom(float evaluation);

	float getEvaluationFrom();

	void setEvaluationTo(float evaluation);

	float getEvaluationTo();
}
