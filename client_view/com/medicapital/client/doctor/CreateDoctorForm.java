package com.medicapital.client.doctor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.doctor.CreateDoctorView;
import com.medicapital.client.ui.UIUtil;

public class CreateDoctorForm extends EditDoctorDataForm implements CreateDoctorView {

	public CreateDoctorForm() {
		userUI.getButtonCheckLogin().setVisible(true);
		userUI.getButtonSave().setVisible(true);
		userUI.getButtonClose().setVisible(true);
	}

	@Override
	public void setCheckLoginFreeHandlerEnabled(boolean enabled) {
		userUI.getButtonCheckLogin().setEnabled(enabled);
	}

	@Override
	public void setLoginEmptyMessageVisible(boolean visible) {
		userUI.getValidationLogin().setVisible(visible);
	}

	@Override
	public void setLoginExistMessageVisible(boolean visible) {
		userUI.getValidationLoginExist().setVisible(visible);
	}

	@Override
	public void setLoginFreeMessageVisible(boolean visible) {
		userUI.getValidationLoginFree().setVisible(visible);
	}

	@Override
	public HasClickHandlers getCreateClickHandler() {
		return userUI.getButtonSave();
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return userUI.getButtonClose();
	}

	@Override
	public void setEntityCreatedMsgVisible(boolean visible) {
		UIUtil.confirm("Rejestracja zakończona pomyślnie");
	}

	@Override
	public void setEntityNotCreatedMsgVisible(boolean visible) {
		UIUtil.error("Rejestracja zakończona błędem");
	}

	@Override
	public void setCreateButtonEnabled(boolean enabled) {
		userUI.getButtonSave().setEnabled(enabled);
	}

	@Override
	public HasClickHandlers getIsLoginFreeClickHandler() {
		return userUI.getButtonCheckLogin();
	}

}
