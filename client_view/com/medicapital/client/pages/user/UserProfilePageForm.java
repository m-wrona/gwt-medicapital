package com.medicapital.client.pages.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.EditDoctorForm;
import com.medicapital.client.user.EditUserForm;

public class UserProfilePageForm extends Composite {

	private static UserProfilePageFormUiBinder uiBinder = GWT.create(UserProfilePageFormUiBinder.class);

	interface UserProfilePageFormUiBinder extends UiBinder<Widget, UserProfilePageForm> {
	}

	@UiField
	SimplePanel dataPanel;

	private EditUserForm editUserForm;
	private EditDoctorForm editDoctorForm;

	public UserProfilePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SimplePanel getDataPanel() {
		return dataPanel;
	}

	public EditDoctorForm getEditDoctorForm() {
		if (editDoctorForm == null) {
			editDoctorForm = new EditDoctorForm();
		}
		return editDoctorForm;
	}

	public EditUserForm getEditUserForm() {
		if (editUserForm == null) {
			editUserForm = new EditUserForm();
		}
		return editUserForm;
	}

}
