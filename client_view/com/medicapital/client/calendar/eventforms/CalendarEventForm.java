package com.medicapital.client.calendar.eventforms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.medicapital.client.calendar.CalendarEventView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.text.RichTextAreaForm;
import com.medicapital.common.entities.CalendarEvent;

public class CalendarEventForm extends PopupableComposite implements CalendarEventView<CalendarEvent> {

	private static CalendarEventViewFormUiBinder uiBinder = GWT.create(CalendarEventViewFormUiBinder.class);

	interface CalendarEventViewFormUiBinder extends UiBinder<Widget, CalendarEventForm> {
	}
	@UiField
	TextBox title;
	@UiField
	RichTextAreaForm description;
	@UiField
	DateBox startTime;
	@UiField
	DateBox endTime;
	@UiField
	Button buttonAdd;
	@UiField
	Button buttonEdit;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonCancel;

	private CalendarEvent event;

	public CalendarEventForm() {
		initWidget(uiBinder.createAndBindUi(this));
		setButtonCloseWindow(buttonCancel);
	}

	@UiHandler("buttonAdd")
	public void addClick(ClickEvent clickEvent) {
		
	}

	@Override
	public void setCalendarEvent(CalendarEvent event) {
		this.event = event;
	}

	@Override
	public CalendarEvent getCalendarEvent() {
		return event;
	}


	@Override
	public void display() {
		startTime.setValue(event.getStartTime());
		endTime.setValue(event.getEndTime());
		title.setText(event.getTitle());
		description.getRichText().setText(event.getDescription());
	}

}
