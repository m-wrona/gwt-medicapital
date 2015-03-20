package com.medicapital.client.pages.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.CreateDoctorForm;
import com.medicapital.client.user.CreateUserForm;

/**
 * Page for registering users and doctors
 * 
 * @author michal
 * 
 */
public class RegisterPageForm extends Composite {

	interface MyUiBinder extends UiBinder<Widget, RegisterPageForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private RegisterPage registerPage;
	@UiField
	PushButton buttonRegisterUser;
	@UiField
	PushButton buttonRegisterDoctor;
	@UiField
	VerticalPanel pagePanel;

	public RegisterPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("buttonRegisterUser")
	public void registerUser(ClickEvent event) {
		if (registerPage != null) {
			registerPage.registerUser();
		}
	}

	@UiHandler("buttonRegisterDoctor")
	public void registerDoctor(ClickEvent event) {
		if (registerPage != null) {
			registerPage.registerDoctor();
		}
	}

	public void setRegisterPage(RegisterPage registerPage) {
		this.registerPage = registerPage;
	}

	public RegisterPage getRegisterPage() {
		return registerPage;
	}

	public VerticalPanel getPagePanel() {
		return pagePanel;
	}

	public CreateDoctorForm getDoctorView() {
		return new CreateDoctorForm();
	}

	public CreateUserForm getUserView() {
		return new CreateUserForm();
	}

}
