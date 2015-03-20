package com.medicapital.client.doctor;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.DoctorView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.entities.User.Sex;

public class AboutDoctorForm extends PopupableComposite implements DoctorView {

	private static AboutDoctorFormUiBinder uiBinder = GWT.create(AboutDoctorFormUiBinder.class);

	interface AboutDoctorFormUiBinder extends UiBinder<Widget, AboutDoctorForm> {
	}

	@UiField
	Label firstName;
	@UiField
	Label lastName;
	@UiField
	Label workId;
	@UiField
	Label specializations;
	@UiField
	Image profileImage;
	@UiField
	HTML notes;

	public AboutDoctorForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setFirstName(String text) {
		firstName.setText(text);
	}

	@Override
	public void setLastName(String text) {
		lastName.setText(text);
	}

	@Override
	public void setWorkId(String text) {
		workId.setText(text);
	}

	@Override
	public void setNotes(String text) {
		notes.setHTML(text);
	}

	@Override
	public void setDoctorProfileImage(String imageUrl) {
		profileImage.setUrl(imageUrl);
	}

	@Override
	public void setPhoneNumber(String text) {
		// empty
	}

	@Override
	public void setMobile(String text) {
		// empty
	}

	@Override
	public void setHobbies(Set<Integer> hobbies) {
		// empty
	}

	@Override
	public void setLastLoginDate(Date date) {
		// empty
	}

	@Override
	public void setLogin(String text) {
		// empty
	}

	@Override
	public void setEmail(String text) {
		// empty
	}

	@Override
	public void setAddress(String text) {
		// empty
	}

	@Override
	public void setPostalCode(String text) {
		// empty
	}

	@Override
	public void setCityId(int cityId) {
		// empty
	}

	@Override
	public void setSex(Sex sex) {
		// empty
	}

	@Override
	public void setPersonalId(String text) {
		// empty
	}

	@Override
	public void setBirthDate(Date birthDate) {
		// empty
	}

	@Override
	public void setAverageEvaluation(float averageEvaluation) {
		// empty
	}

	@Override
    public HasClickHandlers getGoToHomePageClickHandler() {
	    return null;
    }

	@Override
    public HasClickHandlers getGoToCalendarClickHandler() {
	    return null;
    }

}
