package com.medicapital.client.pages;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.exception.AccessDeniedException;
import com.medicapital.common.entities.LoginData;

/**
 * Basic user page. To access this page user must be logged in as a user.
 * 
 * @author mwronski
 * 
 */
abstract public class UserPage<T extends Widget> extends Page<T> {

	@Override
	final protected void checkAccess(final LoginData loginData, Map<String, String> requestParameters) throws AccessDeniedException {
		if (loginData == null) {
			throw new AccessDeniedException("User is not logged in");
		}
	}

}
