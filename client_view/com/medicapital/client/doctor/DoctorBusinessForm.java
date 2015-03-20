package com.medicapital.client.doctor;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.DoctorPresenter;
import com.medicapital.client.doctor.DoctorView;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.common.entities.User.Sex;

public class DoctorBusinessForm extends PopupableComposite implements DoctorView {

	interface MyUiBinder extends UiBinder<Widget, DoctorBusinessForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private DoctorPresenter doctorPresenter;
	private CityListBox cityListBox;

	@UiField
	Label labelEmail;
	@UiField
	Label labelFirstName;
	@UiField
	Label labelLastName;
	@UiField
	Label labelSex;
	@UiField
	Label labelAddress;
	@UiField
	Label labelPostalCode;
	@UiField
	Label labelPhoneNumber;
	@UiField
	Label labelMobile;
	@UiField
	Label labelSpecializations;
	@UiField
	Label labelEvaluation;
	@UiField
	ListBox cities;
	@UiField
	ListBox regions;
	@UiField
	Button buttonDisplayCalendar;
	@UiField
	Button buttonCloseWindow;
	@UiField
	Button buttonDoctorHomePage;

	public DoctorBusinessForm() {
		this(true);
	}

	public DoctorBusinessForm(boolean initWidget) {
		if (initWidget) {
			initWidget(uiBinder.createAndBindUi(this));
			setButtonCloseWindow(buttonCloseWindow);
			cityListBox = new CityListBox(cities, regions);
			cities.setEnabled(false);
			regions.setEnabled(false);
		}
	}

	@UiHandler("buttonDoctorHomePage")
	public void doctorHomePageClick(ClickEvent event) {
		if (doctorPresenter != null) {
			doctorPresenter.displayDoctorHomePage();
		}
	}

	@UiHandler("buttonDisplayCalendar")
	public void showCalendarClick(ClickEvent event) {
		if (doctorPresenter != null) {
			doctorPresenter.displayCalendar();
		}
	}

	@Override
	public void setEmail(String text) {
		labelEmail.setText(text);
	}

	@Override
	public void setFirstName(String text) {
		labelFirstName.setText(text);
	}

	@Override
	public void setLastName(String text) {
		labelLastName.setText(text);
	}

	@Override
	public void setPhoneNumber(String text) {
		labelPhoneNumber.setText(text);
	}

	@Override
	public void setMobile(String text) {
		labelMobile.setText(text);
	}

	@Override
	public void setAverageEvaluation(float averageEvaluation) {
		labelEvaluation.setText("" + averageEvaluation);
	}

	@Override
	public void setAddress(String text) {
		labelAddress.setText(text);
	}

	@Override
	public void setPostalCode(String text) {
		labelPostalCode.setText(text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setWorkId(String text) {
		// ignore
	}

	@Override
	public void setNotes(String text) {
		// ignore
	}

	@Override
	public void setSex(Sex sex) {
		labelSex.setText(Lang.getSex(sex));
	}

	@Override
	public void setDoctorProfileImage(String imageUrl) {
		// ignore
	}

	@Override
	public void setLogin(String text) {
		// ignore
	}

	@Override
	public void setPersonalId(String text) {
		// ignore
	}

	@Override
	public void setHobbies(Set<Integer> hobbies) {
		// ignore
	}

	@Override
	public void setBirthDate(Date date) {
		// ignore
	}

	@Override
	public void setLastLoginDate(Date date) {
		// ignore
	}

	@Override
	public HasClickHandlers getGoToHomePageClickHandler() {
		return buttonDoctorHomePage;
	}

	@Override
	public HasClickHandlers getGoToCalendarClickHandler() {
		return buttonDisplayCalendar;
	}

}
