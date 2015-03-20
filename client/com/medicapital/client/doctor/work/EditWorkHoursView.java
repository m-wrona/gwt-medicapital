package com.medicapital.client.doctor.work;

import com.medicapital.client.core.entity.EditEntityView;
import com.medicapital.common.entities.WorkHours;

public interface EditWorkHoursView extends EditEntityView<WorkHours>, SetterWorkHoursView, GetterWorkHoursView {

	void setEditPresenter(EditWorkHoursPresenter editWorkHoursPresenter);
}
