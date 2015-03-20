package com.medicapital.client.doctor.work;

import java.util.Date;
import com.medicapital.common.entities.Day;

interface SetterWorkHoursView {

	void setDay(Day day);

	void setDateFrom(Date date);

	void setDateTo(Date date);

	void setStartHour(int hour);

	void setStartMinutes(int minutes);

	void setEndHour(int hour);

	void setEndMinutes(int minutes);

	void setDescription(String description);

	void setAddress(String address);

	void setPostalCode(String postalCode);

	void setCityId(int cityId);
}
