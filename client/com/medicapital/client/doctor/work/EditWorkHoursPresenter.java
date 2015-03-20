package com.medicapital.client.doctor.work;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.EditEntityPresenter;
import com.medicapital.common.entities.WorkHours;

public class EditWorkHoursPresenter extends EditEntityPresenter<WorkHours> {

	private final EditWorkHoursView view;

	public EditWorkHoursPresenter(EditWorkHoursView view, EventBus eventBus) {
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
