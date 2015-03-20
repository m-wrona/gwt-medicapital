package com.medicapital.client.calendar;

import java.util.Date;
import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.collections.WorkHoursMap;

public interface CalendarView extends WidgetView {

	/**
	 * Add calendar event
	 * 
	 * @param event
	 */
	void add(CalendarEvent event);

	/**
	 * Remove calendar event
	 * 
	 * @param event
	 */
	void remove(CalendarEvent event);

	/**
	 * Clear events on calendar
	 */
	void clear();

	/**
	 * Create calendar view
	 * 
	 * @param startDate
	 *            first day in view
	 * @param weekView
	 *            days number which should be shown
	 * @param startHour
	 *            begin hour in day's view
	 * @param endHour
	 *            end hour in day's view
	 * @param fieldsPerHour
	 *            number of field per one hour
	 */
	void initView(final Date startDate, final WeekView weekView, final int startHour, final int endHour, int fieldsPerHour);

	void setCalendarPresenter(CalendarPresenter calendarPresenter);

	void setWorkingHours(WorkHoursMap workHoursMap);

}
