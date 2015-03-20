package com.medicapital.common.date;

import com.google.gwt.i18n.client.NumberFormat;

final public class DateFactory {

	private static DateManager dateManager;
	private static DateFormatter dateFormatter;

	public static NumberFormat getTimeFormat() {
		return NumberFormat.getFormat("00");
	}

	public static DateManager createDateManager() {
		if (dateManager == null) {
			dateManager = new DefaultDateManager();
		}
		return dateManager;
	}

	public static DateFormatter createDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DefaultDateFormatter();
		}
		return dateFormatter;
	}

	private DateFactory() {
		// empty
	}
}
