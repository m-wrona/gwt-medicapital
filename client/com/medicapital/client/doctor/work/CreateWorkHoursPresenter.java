package com.medicapital.client.doctor.work;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.common.entities.WorkHours;

public class CreateWorkHoursPresenter extends CreateEntityPresenter<WorkHours> {

	private final CreateWorkHoursView view;

	public CreateWorkHoursPresenter(CreateWorkHoursView view, EventBus eventBus) {
		super(WorkHours.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected WorkHours getEntityFromView() {
		return new WorkHoursViewBinder().getEntityFromView(view, getCurrentEntity());
	}

	@Override
	protected void displayEntityOnView(WorkHours entity) {
		new WorkHoursViewBinder().displayEntityOnView(view, entity);
	}

	@Override
	protected void clearView() {
		new WorkHoursViewBinder().clearView(view);
	}

}
