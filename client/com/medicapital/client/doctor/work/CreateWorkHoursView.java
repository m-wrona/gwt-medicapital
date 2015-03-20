package com.medicapital.client.doctor.work;

import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.common.entities.WorkHours;

public interface CreateWorkHoursView extends CreateEntityView<WorkHours>, SetterWorkHoursView, GetterWorkHoursView {

	void setCreatePresenter(CreateWorkHoursPresenter createWorkHoursPresenter);
}
