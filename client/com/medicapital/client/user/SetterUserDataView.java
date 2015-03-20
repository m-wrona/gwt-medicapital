package com.medicapital.client.user;

import java.util.Date;
import java.util.Set;

import com.medicapital.client.core.entity.EntityView;
import com.medicapital.common.entities.User.Sex;

/**
 * Generic interface to set user's data
 * 
 * @author mwronski
 * 
 */
public interface SetterUserDataView extends EntityView {

	void setLogin(String text);

	void setEmail(String text);

	void setFirstName(String text);

	void setLastName(String text);

	void setAddress(String text);

	void setPostalCode(String text);

	void setCityId(int cityId);

	void setSex(Sex sex);

	void setPersonalId(String text);

	void setBirthDate(Date birthDate);

	void setPhoneNumber(String text);

	void setMobile(String text);

	void setHobbies(Set<Integer> hobbies);

	void setLastLoginDate(Date date);

}
