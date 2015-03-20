package com.medicapital.common.date;

import static com.medicapital.common.util.Util.*;
import java.util.Date;
import com.google.gwt.i18n.client.DateTimeFormat;

final class DefaultDateFormatter implements DateFormatter {

	@Override
	public DateTimeFormat createDateFormat() {
		return DateTimeFormat.getFormat(DATE_FORMAT);
	}

	@Override
	public String toDateString(final Date date) {
		if (date == null) {
			return "";
		}
		return DateTimeFormat.getFormat(DATE_FORMAT).format(date);
	}

	@Override
	public String toDateAndTimeString(final Date date) {
		if (date == null) {
			return "";
		}
		return DateTimeFormat.getFormat(DATE_AND_TIME_FORMAT).format(date);
	}

	@Override
	public String toTimeString(final Date date) {
		if (date == null) {
			return "";
		}
		return DateTimeFormat.getFormat(TIME_FORMAT).format(date);
	}

	@Override
	public String toHourMinuteString(Date date) {
		if (date == null) {
			return "";
		}
		return DateTimeFormat.getFormat(HOUR_MINUTE_FORMAT).format(date);
	}

	@Override
	public Date parseDate(String date) {
		if (isEmpty(date)) {
			return null;
		}
		return DateTimeFormat.getFormat(DATE_FORMAT).parse(date);
	}

	@Override
	public Date parseDate(String year, String month, String day) {
		if (isEmpty(year) || isEmpty(month) || isEmpty(day)) {
			return null;
		}
		return parseDate(year + "-" + month + "-" + day);
	}

	@Override
	public Date parseDateAndTime(String dateAndTime) {
		if (isEmpty(dateAndTime)) {
			return null;
		}
		return DateTimeFormat.getFormat(DATE_AND_TIME_FORMAT).parse(dateAndTime);
	}

}
