package com.medicapital.client.calendar;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.event.DateRequestEvent;
import com.bradrydzewski.gwt.calendar.client.event.DateRequestHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateEvent;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.medicapital.client.calendar.CalendarPresenter;
import com.medicapital.client.calendar.CalendarView;
import com.medicapital.client.calendar.WeekView;
import com.medicapital.client.ui.NumberUtils;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.collections.WorkHoursMap;

public class GoogleCalendarForm extends PopupableComposite implements CalendarView {

	private SimplePanel mainPanel = new SimplePanel();
	private CalendarPresenter calendarPresenter;
	private GoogleCalendarPanel calendarPanel = new GoogleCalendarPanel();

	public GoogleCalendarForm() {
		initWidget(mainPanel);
		mainPanel.add(calendarPanel);
		initHandlers();
	}

	private void initHandlers() {
		calendarPanel.addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {

			@Override
			public void onTimeBlockClick(TimeBlockClickEvent<Date> event) {
				tracer(GoogleCalendarForm.this).debug("TimeBlockClickHandler: " + event.getTarget());
				if (calendarPresenter != null) {
					calendarPresenter.addEventClick(event.getTarget(), event.getTarget());
				}
			}
		});
		calendarPanel.addOpenHandler(new OpenHandler<Appointment>() {

			@Override
			public void onOpen(OpenEvent<Appointment> event) {
				tracer(GoogleCalendarForm.this).debug("OpenHandler: " + event.getTarget().getStart());
				if (calendarPresenter != null) {
					calendarPresenter.editEventClick(toCalendarEvent(event.getTarget()).getId());
				}
			}
		});
		calendarPanel.addDeleteHandler(new DeleteHandler<Appointment>() {

			@Override
			public void onDelete(DeleteEvent<Appointment> event) {
				tracer(GoogleCalendarForm.this).debug("DeleteHandler: " + event.getTarget().getStart());
				if (calendarPresenter != null) {
					calendarPresenter.removeEvent(toCalendarEvent(event.getTarget()).getId());
				}
			}
		});
		calendarPanel.addUpdateHandler(new UpdateHandler<Appointment>() {

			@Override
			public void onUpdate(UpdateEvent<Appointment> event) {
				tracer(GoogleCalendarForm.this).debug("UpdateHandler: " + event.getTarget().getStart());
				if (calendarPresenter != null) {
					calendarPresenter.updateEvent(toCalendarEvent(event.getTarget()));
				}
			}
		});
		calendarPanel.addDateRequestHandler(new DateRequestHandler<Date>() {

			@Override
			public void onDateRequested(DateRequestEvent<Date> event) {
				tracer(GoogleCalendarForm.this).debug("DateRequestHandler: " + event.getTarget());
				displayDays(event.getTarget());
			}
		});
		ClickHandler dateChangeClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				refreshData();
			}
		};
		calendarPanel.getNext().addClickHandler(dateChangeClickHandler);
		calendarPanel.getPrevious().addClickHandler(dateChangeClickHandler);
		calendarPanel.getToday().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				displayDays(new Date());
			}
		});
		calendarPanel.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				displayDays(event.getValue());
			}
		});
	}

	private void displayDays(Date startDate) {
		if (calendarPresenter != null) {
			calendarPanel.setDay(startDate);
			Date dateTo = new Date(startDate.getTime());
			DateManager dateManager = DateFactory.createDateManager();
			dateManager.addDays(dateTo, calendarPanel.getDisplayedDays());
			calendarPresenter.display(startDate, dateTo);
		}
	}

	@Override
	public void setWorkingHours(WorkHoursMap workHoursMap) {
		calendarPanel.setWorkingHours(workHoursMap);
	}

	@Override
	public void add(CalendarEvent event) {
		calendarPanel.addAppointment(toAppointment(event));
	}

	@Override
	public void remove(CalendarEvent event) {
		calendarPanel.removeApointment(toAppointment(event));
	}

	@Override
	public void clear() {
		calendarPanel.clearAppointments();
	}

	@Override
	public void initView(Date startDate, WeekView weekView, int startHour, int endHour, int fieldsPerHour) {
		calendarPanel.setDay(startDate);
		refreshData();
	}

	private void refreshData() {
		calendarPanel.clearAppointments();
		if (calendarPresenter != null) {
			calendarPresenter.display(calendarPanel.getDateFrom(), calendarPanel.getDateTo());
		}
	}

	@Override
	public void setCalendarPresenter(CalendarPresenter calendarPresenter) {
		this.calendarPresenter = calendarPresenter;
	}

	private Appointment toAppointment(CalendarEvent calendarEvent) {
		Appointment appointment = new Appointment();
		appointment.setId("" + calendarEvent.getId());
		appointment.setStart(calendarEvent.getStartTime());
		appointment.setEnd(calendarEvent.getEndTime());
		appointment.setTitle(calendarEvent.getTitle());
		appointment.setDescription(calendarEvent.getDescription());
		return appointment;
	}

	private CalendarEvent toCalendarEvent(Appointment appointment) {
		CalendarEvent calendarEvent = new CalendarEvent();
		calendarEvent.setId(NumberUtils.getInteger(appointment.getId()));
		calendarEvent.setStartTime(appointment.getStart());
		calendarEvent.setEndTime(appointment.getEnd());
		calendarEvent.setTitle(appointment.getTitle());
		calendarEvent.setDescription(appointment.getDescription());
		return calendarEvent;
	}

}
