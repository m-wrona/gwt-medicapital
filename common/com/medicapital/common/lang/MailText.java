package com.medicapital.common.lang;

import com.google.gwt.i18n.client.Constants;

public interface MailText extends Constants {

	String forgottenPasswordTitle();

	String forgottenPassword(String newPassword);

	String passwordChangedTitle();

	String passwordChanged(String newPassword);

	String registrationConfirmationTitle();

	String registrationConfirmation(String firstName, String lastName, String password);
}
