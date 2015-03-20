package com.medicapital.client.calendar;

import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;

abstract public class CalendarEventBroadcastHandler extends CommandRespBroadcastHandler<CalendarEvent> {

	public CalendarEventBroadcastHandler(Object owner) {
		super(owner);
	}

	@Override
	final protected boolean acceptCommand(CommandResp<?> command) {
		return command.getEntityClassName().equals(CalendarEvent.class.getName()) || command.getEntityClassName().equals(PatientVisit.class.getName());
	}

	@Override
	final protected Class<CalendarEvent> getEntityClass() {
		return CalendarEvent.class;
	}

}
