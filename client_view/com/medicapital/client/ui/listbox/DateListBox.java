package com.medicapital.client.ui.listbox;

import java.util.Date;
import com.google.gwt.user.client.ui.ListBox;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.date.DateManager;

public class DateListBox {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private final DateManager dateManager = DateFactory.createDateManager();
	private ListBox year;
	private ListBox month;
	private ListBox day;

	public DateListBox(ListBox year, ListBox month, ListBox day) {
		this.year = year;
		this.month = month;
		this.day = day;
		year.addItem("");
		for (int i = 2030; i >= 1910; i--) {
			year.addItem("" + i);
		}
		month.addItem("");
		for (int i = 1; i <= 12; i++) {
			month.addItem("" + i);
		}
		day.addItem("");
		for (int i = 1; i <= 31; i++) {
			day.addItem("" + i);
		}
	}

	public boolean isDateValid() {
		boolean yearSelected = year.getSelectedIndex() > 0;
		boolean monthSelected = month.getSelectedIndex() > 0;
		boolean daySelected = day.getSelectedIndex() > 0;
		boolean somethingSelected = yearSelected || monthSelected || daySelected;
		boolean allSelected = yearSelected && monthSelected && daySelected;
		return !somethingSelected || allSelected;
	}

	public Date getSelectedDate() {
		return dateFormatter.parseDate(year.getItemText(year.getSelectedIndex()), month.getItemText(month.getSelectedIndex()), day.getItemText(day.getSelectedIndex()));
	}

	public void setDate(Date date) {
		if (date == null) {
			return;
		}
		ListBoxUtils.selectItemOnListBox(year, "" + dateManager.getYear(date));
		ListBoxUtils.selectItemOnListBox(month, "" + dateManager.getMonth(date));
		ListBoxUtils.selectItemOnListBox(day, "" + dateManager.getMonthDay(date));
	}

	public void clear() {
		day.setSelectedIndex(0);
		month.setSelectedIndex(0);
		year.setSelectedIndex(0);
	}
}
