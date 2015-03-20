package com.medicapital.client.pages.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.calendar.CalendarView;
import com.medicapital.client.calendar.GoogleCalendarForm;

public class DoctorCalendarPageForm extends Composite {

	private static DoctorCalendarPageFormUiBinder uiBinder = GWT.create(DoctorCalendarPageFormUiBinder.class);

	interface DoctorCalendarPageFormUiBinder extends UiBinder<Widget, DoctorCalendarPageForm> {
	}

	@UiField
	GoogleCalendarForm calendar;

	public DoctorCalendarPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CalendarView getCalendar() {
		return calendar;
	}

}
