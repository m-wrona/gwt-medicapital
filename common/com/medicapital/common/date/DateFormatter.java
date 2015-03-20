package com.medicapital.common.date;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Formatter enabled to convert dates into proper form
 * 
 * @author mwronski
 * 
 */
public interface DateFormatter {

	String DATE_FORMAT = "yyyy-MM-dd";
	String TIME_FORMAT = "HH:mm:ss";
	String HOUR_MINUTE_FORMAT = "HH:mm";
	String DATE_AND_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

	DateTimeFormat createDateFormat();
	
	/**
	 * Get date string
	 * 
	 * @param date
	 * @return
	 */
	String toDateString(Date date);

	/**
	 * Get date and time string
	 * 
	 * @param date
	 * @return
	 */
	String toDateAndTimeString(Date date);

	/**
	 * Get time string
	 * 
	 * @param date
	 * @return
	 */
	String toTimeString(Date date);

	/**
	 * Get time in form of hours and minutes string
	 * 
	 * @param date
	 * @return
	 */
	String toHourMinuteString(Date date);

	/**
	 * Create date from given date string
	 * 
	 * @param date
	 * @return
	 */
	Date parseDate(String date);

	/**
	 * Create date from given date strings
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	Date parseDate(String year, String month, String day);

	/**
	 * Create date from given date and time string
	 * 
	 * @param date
	 * @return
	 */
	Date parseDateAndTime(String dateAndTime);
}
