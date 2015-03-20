package com.medicapital.client.calendar;

import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.CalendarEvent;

public interface CalendarEventView<T extends CalendarEvent> extends WidgetView {

	/**
	 * Set event for view
	 * 
	 * @param event
	 */
	void setCalendarEvent(T event);

	/**
	 * Get event from view
	 * 
	 * @return
	 */
	T getCalendarEvent();

	/**
	 * Display current event
	 */
	void display();
	
}
