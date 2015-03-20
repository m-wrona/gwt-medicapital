package com.medicapital.client.pages;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.exception.AccessDeniedException;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.entities.UserRole;

/**
 * Basic doctor page. To access this page user must be logged in as a doctor.
 * 
 * @author mwronski
 * 
 */
abstract public class DoctorPage<T extends Widget> extends Page<T> {

	@Override
	final protected void checkAccess(final LoginData loginData, Map<String, String> requestParameters) throws AccessDeniedException {
		if (loginData == null) {
			throw new AccessDeniedException("User is not logged in");
		} else if (loginData.getRole() != UserRole.Doctor) {
			throw new AccessDeniedException("User is logged as " + loginData.getRole() + " - should be: " + UserRole.Doctor);
		}
	}

}
