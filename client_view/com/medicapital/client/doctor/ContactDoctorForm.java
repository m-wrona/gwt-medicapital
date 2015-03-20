package com.medicapital.client.doctor;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.DoctorView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.common.entities.User.Sex;

public class ContactDoctorForm extends PopupableComposite implements DoctorView {

	private static ContactDoctorFormUiBinder uiBinder = GWT.create(ContactDoctorFormUiBinder.class);

	interface ContactDoctorFormUiBinder extends UiBinder<Widget, ContactDoctorForm> {
	}

	@UiField
	Label address;
	@UiField
	Label postalCode;
	@UiField
	ListBox regions;
	@UiField
	ListBox cities;
	@UiField
	Label phoneNumber;
	@UiField
	Label mobileNumber;
	@UiField
	Label eMail;

	private CityListBox cityListBox;

	public ContactDoctorForm() {
		initWidget(uiBinder.createAndBindUi(this));
		cityListBox = new CityListBox(cities, regions);
		cities.setEnabled(false);
		regions.setEnabled(false);
	}

	@Override
	public void setEmail(String text) {
		eMail.setText(text);

	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);

	}

	@Override
	public void setPhoneNumber(String text) {
		phoneNumber.setText(text);

	}

	@Override
	public void setMobile(String text) {
		mobileNumber.setText(text);

	}

	@Override
	public void setAddress(String text) {
		address.setText(text);

	}

	@Override
	public void setPostalCode(String text) {
		postalCode.setText(text);

	}

	@Override
	public void setWorkId(String text) {
		// empty

	}

	@Override
	public void setAverageEvaluation(float averageEvaluation) {
		// empty

	}

	@Override
	public void setNotes(String text) {
		// empty

	}

	@Override
	public void setDoctorProfileImage(String imageUrl) {
		// empty

	}

	@Override
	public void setLogin(String text) {
		// empty

	}

	@Override
	public void setFirstName(String text) {
		// empty

	}

	@Override
	public void setLastName(String text) {
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
	public void setHobbies(Set<Integer> hobbies) {
		// empty

	}

	@Override
	public void setLastLoginDate(Date date) {
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
