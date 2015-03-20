package com.medicapital.client.calendar;

public enum WeekView {

	SingleDay(1), WorkWeek(5), Week(7), Month(31);

	private int value;

	private WeekView(int value) {
		this.value = value;
	}

	public int getInt() {
		return value;
	}
}
