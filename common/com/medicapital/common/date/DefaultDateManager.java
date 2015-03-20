package com.medicapital.common.date;

import java.util.Date;
import com.medicapital.common.entities.Day;

/**
 * Manager enabled operation on dates using deprecated java.util.Date methods.
 * 
 * @author mwronski
 * 
 */
final class DefaultDateManager implements DateManager {

	@Override
	public void moveToNextDay(final Date date, final Day day) {
		do {
			addDays(date, 1);
		} while (getWeekDay(date) != day);
	}

	@Override
	public void moveToPreviousDay(final Date date, final Day day) {
		do {
			addDays(date, -1);
		} while (getWeekDay(date) != day);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDate(Date date, int year, int month, int days) {
		if (year >= 1900) {
			year -= 1900;
		}
		month--; // months are counted from 0
		date.setYear(year);
		date.setMonth(month);
		date.setDate(days);
		setTimeStamp(date, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setTimeStamp(final Date date, final int hour, final int minutes, final int seconds) {
		date.setHours(hour);
		date.setMinutes(minutes);
		date.setSeconds(seconds);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void clearTimeStamp(final Date dateAndTime) {
		dateAndTime.setHours(0);
		dateAndTime.setMinutes(0);
		dateAndTime.setSeconds(0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addDays(final Date date, final int daysNumber) {
		int days = getMonthDay(date);
		days += daysNumber;
		date.setDate(days);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addMinutes(final Date date, final int minutes) {
		int dateMinutes = date.getMinutes();
		dateMinutes += minutes;
		date.setMinutes(dateMinutes);
	}

	@Override
	public boolean after(final Date date, final Date afterDate) {
		return date.after(afterDate);
	}

	@Override
	public boolean afterEqaul(Date date, Date afterDate) {
		return after(date, afterDate) || date.equals(afterDate);
	}

	@Override
	public boolean before(final Date date, final Date beforeDate) {
		return date.before(beforeDate);
	}

	@Override
	public boolean beforeEqual(Date date, Date beforeDate) {
		return before(date, beforeDate) || date.equals(beforeDate);
	}

	@Override
	public int compare(final Date date1, final Date date2) {
		return date1.compareTo(date2);
	}

	@Override
	public int compareWithoutTimeStamps(Date date1, Date date2) {
		Date normalizedDate1 = new Date(date1.getTime());
		clearTimeStamp(normalizedDate1);
		Date normalizedDate2 = new Date(date2.getTime());
		clearTimeStamp(normalizedDate2);
		return compare(normalizedDate1, normalizedDate2);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getYear(final Date date) {
		int year = date.getYear();
		if (year <= 1900) {
			year += 1900;
		}
		return year;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMonth(final Date date) {
		return date.getMonth() + 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMonthDay(final Date date) {
		return date.getDate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Day getWeekDay(final Date date) {
		return Day.valueOf(date.getDay());
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getHours(Date date) {
		return date.getHours();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMinutes(Date date) {
		return date.getMinutes();
	}

}
