package com.medicapital.client.user;

import java.util.Date;
import java.util.Set;

import com.medicapital.client.core.entity.EntityView;
import com.medicapital.common.entities.User.Sex;

/**
 * Generic interface to get user's data
 * 
 * @author mwronski
 * 
 */
public interface GetterUserDataView extends EntityView {

	String getLogin();
	
	String getPassword();

	String getEmail();

	String getFirstName();

	String getLastName();

	String getAddress();

	String getPostalCode();

	int getCityId();

	Sex getSex();

	String getPersonalId();

	Date getBirthDate();
	
	String getPhoneNumber();

	String getMobile();

	Set<Integer> getHobbies();
}
