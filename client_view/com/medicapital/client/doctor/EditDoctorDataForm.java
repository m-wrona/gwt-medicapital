package com.medicapital.client.doctor;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.GetterDoctorDataView;
import com.medicapital.client.doctor.SetterDoctorDataView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.user.EditUserDataForm;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.common.validation.DoctorValidatorView;

class EditDoctorDataForm extends PopupableComposite implements SetterDoctorDataView, GetterDoctorDataView, DoctorValidatorView {

	private static EditDoctorDataFormUiBinder uiBinder = GWT.create(EditDoctorDataFormUiBinder.class);

	interface EditDoctorDataFormUiBinder extends UiBinder<Widget, EditDoctorDataForm> {
	}

	@UiField
	Image profileImage;
	@UiField
	TextBox workId;
	@UiField
	Label averageEvaluation;
	@UiField
	TextArea notes;
	@UiField
	EditUserDataForm userUI;

	public EditDoctorDataForm() {
		initWidget(uiBinder.createAndBindUi(this));
		userUI.getButtonClose().setVisible(false);
		userUI.getButtonDelete().setVisible(false);
		userUI.getButtonSave().setVisible(false);
	}

	EditUserDataForm getUserUI() {
		return userUI;
	}

	@Override
	public void setLogin(String text) {
		userUI.setLogin(text);
	}

	@Override
	public void setEmail(String text) {
		userUI.setEmail(text);
	}

	@Override
	public void setFirstName(String text) {
		userUI.setFirstName(text);
	}

	@Override
	public void setLastName(String text) {
		userUI.setLastName(text);
	}

	@Override
	public void setAddress(String text) {
		userUI.setAddress(text);
	}

	@Override
	public void setPostalCode(String text) {
		userUI.setPostalCode(text);
	}

	@Override
	public void setCityId(int cityId) {
		userUI.setCityId(cityId);
	}

	@Override
	public void setSex(Sex sex) {
		userUI.setSex(sex);
	}

	@Override
	public void setPersonalId(String text) {
		userUI.setPersonalId(text);
	}

	@Override
	public void setBirthDate(Date birthDate) {
		userUI.setBirthDate(birthDate);
	}

	@Override
	public void setPhoneNumber(String text) {
		userUI.setPhoneNumber(text);
	}

	@Override
	public void setMobile(String text) {
		userUI.setPhoneNumber(text);
	}

	@Override
	public void setHobbies(Set<Integer> hobbies) {
		userUI.setHobbies(hobbies);
	}

	@Override
	public void setLastLoginDate(Date date) {
		// ignore
	}

	@Override
	public String getLogin() {
		return userUI.getLogin();
	}

	@Override
	public String getPassword() {
		return userUI.getPassword();
	}

	@Override
	public String getEmail() {
		return userUI.getEmail();
	}

	@Override
	public String getFirstName() {
		return userUI.getFirstName();
	}

	@Override
	public String getLastName() {
		return userUI.getLastName();
	}

	@Override
	public String getAddress() {
		return userUI.getAddress();
	}

	@Override
	public String getPostalCode() {
		return userUI.getPostalCode();
	}

	@Override
	public int getCityId() {
		return userUI.getCityId();
	}

	@Override
	public Sex getSex() {
		return userUI.getSex();
	}

	@Override
	public String getPersonalId() {
		return userUI.getPersonalId();
	}

	@Override
	public Date getBirthDate() {
		return userUI.getBirthDate();
	}

	@Override
	public String getPhoneNumber() {
		return userUI.getPhoneNumber();
	}

	@Override
	public String getMobile() {
		return userUI.getMobile();
	}

	@Override
	public Set<Integer> getHobbies() {
		return userUI.getHobbies();
	}

	@Override
	public String getWorkId() {
		return workId.getText();
	}

	@Override
	public float getAverageEvaluation() {
		return Float.parseFloat(averageEvaluation.getText());
	}

	@Override
	public String getNotes() {
		return notes.getText();
	}

	@Override
	public void setWorkId(String text) {
		workId.setText(text);
	}

	@Override
	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation.setText("" + averageEvaluation);
	}

	@Override
	public void setNotes(String text) {
		notes.setText(text);
	}

	@Override
	public void setDoctorProfileImage(String imageUrl) {
		profileImage.setUrl(imageUrl);
	}

	@Override
    public void setLoginEmptyMsgVisible(boolean visible) {
	    userUI.setLoginEmptyMsgVisible(visible);
    }

	@Override
    public void setWrongEmailMsgVisible(boolean visible) {
		userUI.setWrongEmailMsgVisible(visible);
    }

	@Override
    public void setFirstNameEmptyMsgVisible(boolean visible) {
		userUI.setFirstNameEmptyMsgVisible(visible);
    }

	@Override
    public void setLastNameEmptyMsgVisible(boolean visible) {
		userUI.setLastNameEmptyMsgVisible(visible);
    }

	@Override
    public boolean validateOptionalData() {
		return userUI.validateOptionalData();
    }

}
