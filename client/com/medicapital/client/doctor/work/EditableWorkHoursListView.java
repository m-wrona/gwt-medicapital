package com.medicapital.client.doctor.work;

import java.util.Date;

import com.medicapital.client.core.entities.EditableEntitiesView;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.WorkHours;

public interface EditableWorkHoursListView extends EditableEntitiesView<WorkHours> {

	void display(int workHourId, Day day, String workHours, String address, String description);

	void display(int workHourId, Date dateFrom, Date dateTo, String workHours, String address, String description);

	void setEditWorkHoursPresenter(EditableWorkHoursListPresenter editPresenter);
}
