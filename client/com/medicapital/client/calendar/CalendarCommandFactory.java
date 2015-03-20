package com.medicapital.client.calendar;

import java.util.Date;
import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.common.commands.calendar.DeleteCalendarEventCommand;
import com.medicapital.common.commands.calendar.SelectCalendarEventCommand;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.CalendarEvent;

public class CalendarCommandFactory extends EntityCommandFactory<CalendarEvent> {

	private int patientId;
	private int doctorId;

	public CalendarCommandFactory() {
		super(CalendarEvent.class);
	}

	public <T extends CalendarEvent> DeleteCalendarEventCommand createDeleteCommand(T event) {
		DeleteCalendarEventCommand deleteCommand = new DeleteCalendarEventCommand(event.getId(), event.getClass());
		return deleteCommand;
	}

	public SelectCommand<CalendarEvent> createSelectCommnad(final Date startTime, final Date endTime) {
		final SelectCalendarEventCommand selectCommand = new SelectCalendarEventCommand();
		selectCommand.setDoctorId(doctorId);
		selectCommand.setPatientId(patientId);
		selectCommand.setBeginDate(startTime);
		selectCommand.setEndDate(endTime);
		return selectCommand;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(final int patientId) {
		this.patientId = patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(final int doctorId) {
		this.doctorId = doctorId;
	}

}
