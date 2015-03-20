package com.medicapital.client.lang;

import com.google.gwt.core.client.GWT;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.common.lang.GenericText;
import com.medicapital.common.lang.RichTextAreaText;
import com.medicapital.common.lang.WarningText;

final public class Lang {

	private static WarningText warningText;
	private static GenericText genericText;
	private static RichTextAreaText richTextAreaText;

	public static String getDay(Day day) {
		switch (day) {
		case Monday:
			return generic().monday();

		case Tuesday:
			return generic().tuesday();

		case Wednesday:
			return generic().wednesday();

		case Thursday:
			return generic().thursday();

		case Friday:
			return generic().friday();

		case Saturday:
			return generic().saturday();

		case Sunday:
			return generic().sunday();

		default:
			return day.toString();
		}
	}

	/**
	 * Get rating titles
	 * 
	 * @return
	 */
	public static String[] getRatingTitles() {
		final String[] titles = new String[6];
		titles[0] = generic().bad();
		titles[1] = generic().unsatisfactory();
		titles[2] = generic().average();
		titles[3] = generic().good();
		titles[4] = generic().veryGood();
		titles[5] = generic().excellent();
		return titles;
	}

	/**
	 * Get sex translation
	 * 
	 * @param sex
	 * @return
	 */
	public static String getSex(final Sex sex) {
		if (sex == null) {
			return "";
		} else if (sex == Sex.Male) {
			return generic().male();
		} else {
			return generic().female();
		}
	}

	public static RichTextAreaText richTextArea() {
		if (richTextAreaText == null) {
			richTextAreaText = GWT.create(RichTextAreaText.class);
		}
		return richTextAreaText;
	}

	public static WarningText warnings() {
		if (warningText == null) {
			warningText = GWT.create(WarningText.class);
		}
		return warningText;
	}

	static void setWarningText(WarningText warningText) {
		Lang.warningText = warningText;
	}

	public static GenericText generic() {
		if (genericText == null) {
			genericText = GWT.create(GenericText.class);
		}
		return genericText;
	}

	static void setGenericText(GenericText genericText) {
		Lang.genericText = genericText;
	}

	private Lang() {
		// empty
	}
}
