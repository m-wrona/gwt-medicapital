package com.medicapital.client.visit;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.calendar.CalendarEventView;
import com.medicapital.client.ui.GoogleMapPresenter;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.client.visit.VisitPresenter;
import com.medicapital.client.visit.VisitPresenterView;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.PatientVisit;

public class VisitForm extends PopupableComposite implements VisitPresenterView, CalendarEventView<PatientVisit> {

	interface MyUiBinder extends UiBinder<Widget, VisitForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private VisitPresenter visitPresenter;
	private CityListBox cityListBox;

	@UiField
	TextBox visitTitle;
	@UiField
	TextBox visitDate;
	@UiField
	Hyperlink visitDoctor;
	@UiField
	TextArea visitDescription;
	@UiField
	TextBox visitAddress;
	@UiField
	PushButton buttonCloseWindow;
	@UiField
	MapWidget googleMapWidget;
	@UiField
	PushButton buttonNewEvaluation;
	@UiField
	PushButton buttonShowEvaluation;
	@UiField
	ListBox cities;
	@UiField
	ListBox regions;
	@UiField
	Label patient;

	public VisitForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initForm();
	}

	protected void initForm() {
		visitTitle.setEnabled(false);
		visitDescription.setEnabled(false);
		visitDate.setEnabled(false);
		visitAddress.setEnabled(false);
		visitTitle.setEnabled(false);
		cities.setEnabled(false);
		regions.setEnabled(false);

		googleMapWidget.addControl(new LargeMapControl());
		cityListBox = new CityListBox(cities, regions);
		setPushButtonCloseWindow(buttonCloseWindow);
	}

	@Override
	public void setPatient(String firstName, String lastName) {
		patient.setText(firstName + " " + lastName);
	}

	@Override
	public void setCalendarEvent(PatientVisit event) {
		// TODO Auto-generated method stub

	}

	@Override
	public PatientVisit getCalendarEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEvaluationButtonVisible(boolean visible) {
		buttonNewEvaluation.setVisible(visible);
	}

	@UiHandler("buttonShowEvaluation")
	public void showEvaluationClick(ClickEvent event) {
		if (visitPresenter != null) {
			visitPresenter.displayEvaluation();
		}
	}

	@UiHandler("buttonNewEvaluation")
	public void createEvaluationClick(ClickEvent event) {
		if (visitPresenter != null) {
			visitPresenter.createEvaluation();
		}
	}

	@Override
	public void setNotes(String text) {
		visitDescription.setText(text);
	}

	@Override
	public void setBeginDate(Date beginDate) {
		visitDate.setText(dateFormatter.toDateAndTimeString(beginDate));
	}

	@Override
	public void setEndDate(Date endDate) {
		// empty
	}

	@Override
	public void setDoctor(String firstName, String lastName) {
		visitDoctor.setText(firstName + " " + lastName);
	}

	@Override
	public void setAddress(String text) {
		visitAddress.setText(text);
		GoogleMapPresenter.getInstance().getLocalization(googleMapWidget, cityListBox.getSelectedCityName(), text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setDisplayEvaluationVisible(boolean visible) {
		buttonShowEvaluation.setVisible(visible);
	}

	@Override
	public void setCreateEvaluationVisible(boolean visible) {
		buttonNewEvaluation.setVisible(visible);
	}

	@Override
	public void setVisitTitle(String text) {
		visitTitle.setText(text);
	}

	public void setVisitPresenter(VisitPresenter visitPresenter) {
		this.visitPresenter = visitPresenter;
	}

	public VisitPresenter getVisitPresenter() {
		return visitPresenter;
	}

}
