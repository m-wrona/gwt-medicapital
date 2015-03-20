package com.medicapital.client.ui;

final public class NumberUtils {

	public static Integer getInteger(String text, int defaultValue) {
		if (text == null || text.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Integer getInteger(String text) {
		if (text == null || text.isEmpty()) {
			return new Integer(0);
		}
		return Integer.parseInt(text);
	}

	public static Float getFloat(String text, float defaultValue) {
		if (text == null || text.isEmpty()) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(text);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Float getFloat(String text) {
		if (text == null || text.isEmpty()) {
			return new Float(0);
		}
		return Float.parseFloat(text);
	}

	private NumberUtils() {
		// emptyF
	}
}
