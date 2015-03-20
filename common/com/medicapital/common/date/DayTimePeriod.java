package com.medicapital.common.date;

import java.util.Date;
import com.medicapital.common.entities.Day;

/**
 * Class represents day time period which is expressed by day, start hour, start
 * minutes, end hour and end minutes.
 * 
 * @author mwronski
 * 
 */
final public class DayTimePeriod {

	private static final DateManager dateManager = DateFactory.createDateManager();
	private final Day day;
	private final Date dayDate;
	private final int startHour;
	private final int startMinutes;
	private final int endHour;
	private final int endMinutes;

	public DayTimePeriod(Day day, int startHour, int startMinutes, int endHour, int endMinutes) {
		this.day = day;
		this.startHour = startHour;
		this.startMinutes = startMinutes;
		this.endHour = endHour;
		this.endMinutes = endMinutes;
		this.dayDate = null;
	}

	public DayTimePeriod(Date dayDateFrom, Date dayDateTo) {
		if (dateManager.compareWithoutTimeStamps(dayDateFrom, dayDateTo) != 0) {
			throw new IllegalArgumentException("Dates must be from the same day - date from: " + dayDateFrom + ", date to: " + dayDateTo);
		}
		day = dateManager.getWeekDay(dayDateFrom);
		dayDate = new Date(dayDateFrom.getTime());
		dateManager.clearTimeStamp(dayDate);
		startHour = dateManager.getHours(dayDateFrom);
		startMinutes = dateManager.getMinutes(dayDateFrom);
		endHour = dateManager.getHours(dayDateTo);
		endMinutes = dateManager.getMinutes(dayDateTo);
	}

	/**
	 * Check whether time period is inside this day time priod
	 * 
	 * @param timePeriod
	 * @return
	 */
	public boolean contains(TimePeriod timePeriod) {
		return contains(timePeriod.getDateFrom(), timePeriod.getDateTo());
	}

	/**
	 * Check whether dates are inside this day time period
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public boolean contains(Date dateFrom, Date dateTo) {
		return contains(dateFrom) && contains(dateTo);
	}

	/**
	 * Check whether date is inside this day time period
	 * 
	 * @param date
	 * @return
	 */
	public boolean contains(Date date) {
		if (dateManager.getWeekDay(date) != day) {
			return false;
		} else if (dayDate != null && dateManager.compareWithoutTimeStamps(dayDate, date) != 0) {
			return false;
		}
		Date dayFrom = new Date(date.getTime());
		dateManager.setTimeStamp(dayFrom, startHour, startMinutes, 0);
		Date dayTo = new Date(date.getTime());
		dateManager.setTimeStamp(dayTo, endHour, endMinutes, 0);
		return new TimePeriod(dayFrom, dayTo).contains(date);
	}

	public boolean contains(DayTimePeriod dayTimePeriod) {
		if (day != dayTimePeriod.day) {
			return false;
		} else if (dayDate != null && !dayDate.equals(dayTimePeriod.getDayDate())) {
			return false;
		} else if (dayTimePeriod.startHour < startHour || dayTimePeriod.endHour > endHour) {
			return false;
		} else if (dayTimePeriod.startHour == startHour && dayTimePeriod.startMinutes < startMinutes) {
			return false;
		} else if (dayTimePeriod.endHour == endHour && dayTimePeriod.endMinutes > endMinutes) {
			return false;
		}
		return true;
	}

	/**
	 * Get precise date of period if it's known
	 * 
	 * @return date or null
	 */
	public Date getDayDate() {
		return dayDate;
	}

	public Day getDay() {
		return day;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getStartMinutes() {
		return startMinutes;
	}

	public int getEndHour() {
		return endHour;
	}

	public int getEndMinutes() {
		return endMinutes;
	}

	@Override
	public String toString() {
		return "[" + day + ", " + startHour + ":" + startMinutes + "-" + endHour + ":" + endMinutes + "]";
	}
}
