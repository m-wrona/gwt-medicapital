package com.medicapital.common.commands.calendar;

import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.entities.CalendarEvent;

public class DeleteCalendarEventCommand extends DeleteCommand<CalendarEvent> {

	private String eventClassName;

	/**
	 * Constructor for RPC communication
	 */
	protected DeleteCalendarEventCommand() {
		// empty
	}

	public DeleteCalendarEventCommand(int elementId, Class<? extends CalendarEvent> eventClass) {
		super(CalendarEvent.class, elementId);
		this.eventClassName = eventClass.getName();
	}

	public String getEventClassName() {
		return eventClassName;
	}

}