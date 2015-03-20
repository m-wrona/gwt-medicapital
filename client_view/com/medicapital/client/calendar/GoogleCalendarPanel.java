package com.medicapital.client.calendar;

import java.util.Date;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarFormat;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.event.DateRequestHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.collections.WorkHoursMap;

class GoogleCalendarPanel extends Composite {

	private static GoogleCalendarPanelUiBinder uiBinder = GWT.create(GoogleCalendarPanelUiBinder.class);

	interface GoogleCalendarPanelUiBinder extends UiBinder<Widget, GoogleCalendarPanel> {
	}

	@UiField
	Calendar calendar;
	@UiField
	DecoratedTabBar calendarViews;
	@UiField
	PushButton previous;
	@UiField
	PushButton next;
	@UiField
	PushButton today;
	@UiField
	DateBox date;

	private final CalendarSettings settings = new CalendarSettings();
	private final DateManager dateManager = DateFactory.createDateManager();
	private final DateTimeFormat dateFormat = DateFactory.createDateFormatter().createDateFormat();

	public GoogleCalendarPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		initCalendar();
		initCalendarViews();
		initHandlers();
		initHourFormatting();
		calendar.doLayout();
		date.setFormat(new DateBox.Format() {

			@Override
			public void reset(DateBox dateBox, boolean abandon) {
				// empty
			}

			@Override
			public Date parse(DateBox dateBox, String text, boolean reportError) {
				return dateFormat.parse(text);
			}

			@Override
			public String format(DateBox dateBox, Date date) {
				return date == null ? "" : dateFormat.format(date);
			}
		});
	}

	private void initHourFormatting() {
		CalendarFormat.INSTANCE.setTimeFormat("HH:mm");
		CalendarFormat.INSTANCE.setAm("");
		CalendarFormat.INSTANCE.setPm("");
		CalendarFormat.INSTANCE.setNoon("12:00");
	}

	private void initHandlers() {
		previous.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Date date = calendar.getDate();
				dateManager.addDays(date, -calendar.getDays());
				calendar.setDate(date);
				GoogleCalendarPanel.this.date.setValue(date, false);
			}
		});
		next.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Date date = calendar.getDate();
				dateManager.addDays(date, calendar.getDays());
				calendar.setDate(date);
				GoogleCalendarPanel.this.date.setValue(date, false);
			}
		});
	}

	private void initCalendar() {
		settings.setOffsetHourLabels(false);
		settings.setTimeBlockClickNumber(Click.Double);
		settings.setEnableDragDrop(true);
		calendar.setSettings(settings);
	}

	private void initCalendarViews() {
		// TODO add lang
		calendarViews.addTab("Dzień");
		calendarViews.addTab("Tydzień pracy");
		calendarViews.addTab("Tydzień");
		calendarViews.addTab("Miesiąc");
		calendarViews.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabIndex = event.getSelectedItem();
				if (tabIndex == 0) {
					calendar.setView(CalendarViews.DAY, WeekView.SingleDay.getInt());
				} else if (tabIndex == 1)
					calendar.setView(CalendarViews.DAY, WeekView.WorkWeek.getInt());
				else if (tabIndex == 2)
					calendar.setView(CalendarViews.DAY, WeekView.Week.getInt());
				else if (tabIndex == 3)
					calendar.setView(CalendarViews.MONTH);
			}
		});
		calendarViews.selectTab(2);
		calendar.setView(CalendarViews.DAY, WeekView.Week.getInt());

	}

	void addAppointment(Appointment appointment) {
		calendar.addAppointment(appointment);
	}

	void removeApointment(Appointment appointment) {
		calendar.removeAppointment(appointment);
	}

	void clearAppointments() {
		calendar.clearAppointments();
	}

	void addDeleteHandler(DeleteHandler<Appointment> handler) {
		calendar.addDeleteHandler(handler);
	}

	void addUpdateHandler(UpdateHandler<Appointment> handler) {
		calendar.addUpdateHandler(handler);
	}

	void addOpenHandler(OpenHandler<Appointment> handler) {
		calendar.addOpenHandler(handler);
	}

	void addDateRequestHandler(DateRequestHandler<Date> handler) {
		calendar.addDateRequestHandler(handler);
	}

	void addMouseOverHandler(MouseOverHandler<Appointment> handler) {
		calendar.addMouseOverHandler(handler);
	}

	void addSelectionHandler(SelectionHandler<Appointment> handler) {
		calendar.addSelectionHandler(handler);
	}

	void addTimeBlockClickHandler(TimeBlockClickHandler<Date> handler) {
		calendar.addTimeBlockClickHandler(handler);
	}

	@SuppressWarnings("deprecation")
	void setDay(Date date) {
		// TODO
		clearAppointments();
		if (calendarViews.getSelectedTab() == 0) {
			// day view
			calendar.setDate(date);
		} else if (calendarViews.getSelectedTab() == 3) {
			// month view
			Date calendarDate = new Date(date.getTime());
			calendarDate.setDate(1);
			calendar.setDate(calendarDate);
		} else {
			// week view
			Date calendarDate = new Date(date.getTime());
			dateManager.moveToPreviousDay(calendarDate, Day.Monday);
			calendar.setDate(calendarDate);
		}
		this.date.setValue(date, false);
	}

	Date getDateFrom() {
		return calendar.getDate();
	}

	Date getDateTo() {
		Date dateTo = new Date(getDateFrom().getTime());
		dateManager.addDays(dateTo, calendar.getDays());
		return dateTo;
	}

	int getDisplayedDays() {
		return calendar.getDays();
	}

	PushButton getNext() {
		return next;
	}

	PushButton getPrevious() {
		return previous;
	}

	PushButton getToday() {
		return today;
	}

	DateBox getDatePicker() {
		return date;
	}

	public void setWorkingHours(WorkHoursMap workHoursMap) {
		settings.setWorkHoursMap(workHoursMap);
		calendar.doLayout();
	}

}
