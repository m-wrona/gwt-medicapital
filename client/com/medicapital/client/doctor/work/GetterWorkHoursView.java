package com.medicapital.client.doctor.work;

import java.util.Date;
import com.medicapital.common.entities.Day;

interface GetterWorkHoursView {

	Day getDay();

	Date getDateFrom();

	Date getDateTo();

	int getStartHour();

	int getStartMinutes();

	int getEndHours();

	int getEndMinutes();

	String getDescription();

	String getAddress();

	String getPostalCode();

	int getCityId();
}
