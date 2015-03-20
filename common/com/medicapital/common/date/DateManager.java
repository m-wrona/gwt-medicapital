package com.medicapital.common.date;

import java.util.Date;

import com.medicapital.common.entities.Day;

/**
 * Manager allows to perform date operations on dates on client side. This
 * interface is needed because of lack of the standard date library which
 * supports Calendar, Locale etc. on client's side Unfortunately now dates must
 * be handled using deprecated methods from java.util.Date.
 * 
 * @author mwronski
 * 
 */
public interface DateManager {

	/**
	 * Move the date to next following day. If dates represents Friday on
	 * 20.05.XXXX and day is given as Sunday then date will be moved to Sunday
	 * 22.05.XXXX.
	 * 
	 * @param date
	 * @param day
	 */
	void moveToNextDay(Date date, Day day);

	/**
	 * Move the date to previous day. If dates represents Friday on 20.05.XXXX
	 * and day is given as Wednesday then date will be moved to Wednesday
	 * 18.05.XXXX.
	 * 
	 * @param date
	 * @param day
	 */
	void moveToPreviousDay(Date date, Day day);

	/**
	 * Set date stamp in date
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param days
	 */
	void setDate(Date date, int year, int month, int days);

	/**
	 * Set time stamp in in date
	 * 
	 * @param date
	 * @param hour
	 * @param minutes
	 * @param seconds
	 */
	void setTimeStamp(Date date, int hour, int minutes, int seconds);

	/**
	 * Clear time stamp in date and time
	 * 
	 * @param date
	 */
	void clearTimeStamp(Date dateAndTime);

	/**
	 * Add minutes number into the date
	 * 
	 * @param date
	 * @param daysNumber
	 */
	void addMinutes(Date date, int minutes);

	/**
	 * Add days number into the date
	 * 
	 * @param date
	 * @param daysNumber
	 */
	void addDays(Date date, int daysNumber);

	/**
	 * Check if date if after afterDate
	 * 
	 * @param date
	 * @param afterDate
	 * @return
	 */
	boolean after(Date date, Date afterDate);

	/**
	 * Check if date if after afterDate of equal afterDate
	 * 
	 * @param date
	 * @param afterDate
	 * @return
	 */
	boolean afterEqaul(Date date, Date afterDate);

	/**
	 * Check if date is before beforeDate
	 * 
	 * @param date
	 * @param beforeDate
	 * @return
	 */
	boolean before(Date date, Date beforeDate);

	/**
	 * Check if date is before beforeDate or equal beforeDate
	 * 
	 * @param date
	 * @param beforeDate
	 * @return
	 */
	boolean beforeEqual(Date date, Date beforeDate);

	/**
	 * Compare dates
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	int compare(Date date1, Date date2);

	/**
	 * Compare dates omitting time stamps
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	int compareWithoutTimeStamps(Date date1, Date date2);

	/**
	 * Get year number from the date
	 * 
	 * @param date
	 * @return
	 */
	int getYear(Date date);

	/**
	 * Get month number from the date
	 * 
	 * @param date
	 * @return
	 */
	int getMonth(Date date);

	/**
	 * Get hours from date
	 * 
	 * @param date
	 * @return
	 */
	int getHours(Date date);

	/**
	 * Get minutes from date
	 * 
	 * @param date
	 * @return
	 */
	int getMinutes(Date date);

	/**
	 * Get day number from the date
	 * 
	 * @param date
	 * @return
	 */
	int getMonthDay(Date date);

	/**
	 * Get week date from date
	 * 
	 * @param date
	 * @return
	 */
	Day getWeekDay(Date date);
}
