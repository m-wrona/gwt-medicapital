package com.medicapital.client.calendar.access;

import java.util.Date;

import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.CalendarEvent;

/**
 * Access controllers for calendar events
 * 
 * @author mwronski
 * 
 */
final public class CalendarEventAccessController {

	private static final DateManager dateManager = DateFactory.createDateManager();

	/**
	 * Check if event can be edited
	 * 
	 * @param event
	 * @return
	 */
	final public static boolean isEventEditable(final CalendarEvent event) {
		return dateManager.after(event.getStartTime(), new Date());
	}

	private CalendarEventAccessController() {
		// no instances allowed
	}
}
