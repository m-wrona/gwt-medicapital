package com.medicapital.client.visit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.medicapital.client.ui.GoogleMapPresenter;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.client.ui.text.RichTextAreaForm;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.VisitType;

class EditVisitDataForm extends PopupableComposite implements EditVisitDataView {

	private static VisitDataFormUiBinder uiBinder = GWT.create(VisitDataFormUiBinder.class);

	interface VisitDataFormUiBinder extends UiBinder<Widget, EditVisitDataForm> {
	}

	@UiField
	ListBox eventType;
	@UiField
	TextBox title;
	@UiField
	RichTextAreaForm description;
	@UiField
	IntegerBox durationTime;
	@UiField
	DateBox startTime;
	@UiField
	DateBox endTime;
	@UiField
	Button buttonAdd;
	@UiField
	Button buttonCancel;
	@UiField
	Button buttonDelete;
	@UiField
	Anchor doctor;
	@UiField
	Anchor patient;
	@UiField
	ListBox regions;
	@UiField
	ListBox cities;
	@UiField
	TextBox address;
	@UiField
	MapWidget googleMapWidget;
	@UiField
	Label labelEventType;
	@UiField
	Label labelDuration;
	@UiField
	Button buttonFindPatient;

	private final Map<String, VisitType> visitTypes = new HashMap<String, VisitType>();
	private CityListBox cityListBox;
	private PatientVisit visit;

	public EditVisitDataForm() {
		initWidget(uiBinder.createAndBindUi(this));
		googleMapWidget.addControl(new LargeMapControl());
		cityListBox = new CityListBox(cities, regions);
		initVisitTypesHandler();
	}

	@Override
	public void setPatient(String firstName, String lastName) {
		patient.setText(firstName + " " + lastName);
	}

	@Override
	public String getVisitTitle() {
		return title.getText();
	}

	@Override
	public void setVisitTypes(Set<VisitType> visitTypes) {
		this.visitTypes.clear();
		getEventType().clear();
		for (VisitType visitType : visitTypes) {
			this.visitTypes.put(visitType.getName(), visitType);
			getEventType().addItem(visitType.getName());
		}
		initVisitTypesHandler();
		getEventType().setSelectedIndex(0);
	}

	protected void initVisitTypesHandler() {
		// empty
	}

	@Override
	public void setCalendarEvent(PatientVisit event) {
		this.visit = event;

	}

	@Override
	public PatientVisit getCalendarEvent() {
		return visit;
	}

	@Override
	public void display() {
		startTime.setValue(visit.getStartTime());
		endTime.setValue(visit.getEndTime());
		title.setText(visit.getTitle());
		description.getRichText().setText(visit.getDescription());
	}

	@Override
	public void setBookingErrorVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBookingOkVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBookingOnGoinMsgVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisitTitle(String text) {
		title.setText(text);
	}

	@Override
	public String getNotes() {
		return description.getRichText().getText();
	}

	@Override
	public Date getBeginDate() {
		return startTime.getValue();
	}

	@Override
	public Date getEndDate() {
		return endTime.getValue();
	}

	@Override
	public void setNotes(String text) {
		description.getRichText().setText(text);
	}

	@Override
	public void setBeginDate(Date beginDate) {
		startTime.setValue(beginDate);
	}

	@Override
	public void setEndDate(Date endDate) {
		endTime.setValue(endDate);
	}

	@Override
	public void setDoctor(String firstName, String lastName) {
		doctor.setText(firstName + " " + lastName);
	}

	@Override
	public String getAdress() {
		return address.getText();
	}

	@Override
	public int getCityId() {
		return cityListBox.getSelectedCityId();
	}

	@Override
	public void setAddress(String text) {
		address.setText(text);
		GoogleMapPresenter.getInstance().getLocalization(googleMapWidget, cityListBox.getSelectedCityName(), text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setVisitTimeEditable(boolean enabled) {
		startTime.setEnabled(enabled);
		endTime.setEnabled(enabled);
	}

	@Override
	public void setDurationTime(int time) {
		durationTime.setText("" + time);
	}

	protected Button getButtonAdd() {
		return buttonAdd;
	}

	protected Button getButtonCancel() {
		return buttonCancel;
	}

	protected Button getButtonDelete() {
		return buttonDelete;
	}

	protected ListBox getEventType() {
		return eventType;
	}

	protected Map<String, VisitType> getVisitTypes() {
		return visitTypes;
	}

}
