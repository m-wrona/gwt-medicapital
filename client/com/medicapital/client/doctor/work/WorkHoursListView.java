package com.medicapital.client.doctor.work;

import java.util.Date;
import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.WorkHours;

public interface WorkHoursListView extends EntitiesView<WorkHours> {

	void display(int workHourId, Day day, String workHours, String address, String description);

	void display(int workHourId, Date dateFrom, Date dateTo, String workHours, String address, String description);
	
	void setWorkHoursListPresenter(WorkHoursListPresenter workHoursListPresenter);
}
