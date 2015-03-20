package com.medicapital.common.testutil;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.medicapital.client.exception.UnsupportedOperationException;
import com.medicapital.common.date.DateFormatter;

/**
 * Test date formatter for client side
 * 
 * @author mwronski
 * 
 */
final public class TestDateFormatter implements DateFormatter {

	@Override
	public DateTimeFormat createDateFormat() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toTimeString(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toHourMinuteString(Date date) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toDateString(Date date) {
		return date == null ? null : "" + date.getYear() + "-" + date.getMonth() + "-" + date.getDate();
	}

	@Override
	public String toDateAndTimeString(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date parseDateAndTime(String dateAndTime) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date parseDate(String year, String month, String day) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date parseDate(String date) {
		throw new UnsupportedOperationException();
	}
}
