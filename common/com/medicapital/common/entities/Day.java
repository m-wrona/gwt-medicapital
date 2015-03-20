package com.medicapital.common.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Day implements IsSerializable {

	Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6), Sunday(0);

	private int dayValue;

	private Day(int dayValue) {
		this.dayValue = dayValue;
	}

	public int getInt() {
		return dayValue;
	}

	/**
	 * Get value of day
	 * 
	 * @param dayValue
	 *            0-Sunday,1-Monday,...,6-Saturday
	 * @return
	 * @throws IllegalArgumentException
	 *             when day value doesn't represent any day of the week
	 */
	public static Day valueOf(int dayValue) throws IllegalArgumentException {
		for (Day day : Day.values()) {
			if (day.getInt() == dayValue) {
				return day;
			}
		}
		throw new IllegalArgumentException("Day value " + dayValue + " doesn't represent any day of week");
	}
}
