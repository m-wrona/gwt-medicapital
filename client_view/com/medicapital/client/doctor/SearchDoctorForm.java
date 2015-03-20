package com.medicapital.client.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.UIFactory;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.RatingForm;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.client.ui.listbox.ElementListBox;
import com.medicapital.common.entities.User.Sex;

public class SearchDoctorForm extends PopupableComposite implements SearchDoctorCriteriaView {

	interface MyUiBinder extends UiBinder<Widget, SearchDoctorForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private CityListBox cityListBox;
	private ElementListBox<Sex> sexListBox;

	@UiField
	TextBox firstName;
	@UiField
	TextBox lastName;
	@UiField
	ListBox sex;
	@UiField
	TextBox address;
	@UiField
	ListBox cities;
	@UiField
	ListBox regions;
	@UiField
	Button buttonSearch;
	@UiField
	Button buttonCancel;
	@UiField
	RatingForm evaluationFrom;
	@UiField
	RatingForm evaluationTo;

	public SearchDoctorForm() {
		this(true);
	}

	public SearchDoctorForm(boolean initWidget) {
		if (initWidget) {
			initWidget(uiBinder.createAndBindUi(this));
			cityListBox = new CityListBox(cities, regions);
			sexListBox = UIFactory.createSexListBox(sex);
		}
	}

	public Button getButtonSearch() {
		return buttonSearch;
	}

	public Button getButtonCancel() {
		return buttonCancel;
	}

	@Override
	public String getFirstName() {
		return firstName.getText();
	}

	@Override
	public String getLastName() {
		return lastName.getText();
	}

	@Override
	public String getAddress() {
		return address.getText();
	}

	@Override
	public int getCityId() {
		return cityListBox.getSelectedCityId();
	}

	@Override
	public Sex getSex() {
		return sexListBox.getSelectedElement();
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
	public void setAddress(String text) {
		address.setText(text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setSex(Sex sex) {
		sexListBox.setElement(sex);
	}

	@Override
	public void setEvaluationFrom(float evaluation) {
		evaluationFrom.setRating((int) evaluation);
	}

	@Override
	public float getEvaluationFrom() {
		return evaluationFrom.getRating();
	}

	@Override
	public float getEvaluationTo() {
		return evaluationTo.getRating();
	}

	@Override
	public void setEvaluationTo(float evaluation) {
		evaluationTo.setRating((int) evaluation);
	}

}
