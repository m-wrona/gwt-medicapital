package com.medicapital.client.doctor.work;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.WorkHours;

public class WorkHoursFactory {

	private EntityCommandFactory<WorkHours> commandFactory = new EntityCommandFactory<WorkHours>(WorkHours.class);

	public EditableWorkHoursListPresenter createEditWorkHoursList(EditableWorkHoursListView view, CreateWorkHoursView createWorkHoursView, EditWorkHoursView editWorkHoursView) {
		EditableWorkHoursListPresenter editableWorkHoursList = new EditableWorkHoursListPresenter(view, DaoFactory.getEventBus());
		editableWorkHoursList.setDisplayCommandFactory(commandFactory);
		editableWorkHoursList.setEditCommandFactory(commandFactory);
		editableWorkHoursList.setServiceAccess(DaoFactory.getServiceAccess());
		editableWorkHoursList.setCreateWorkHoursPresenter(createCreateWorkHoursPresenter(createWorkHoursView));
		editableWorkHoursList.setEditWorkHoursPresenter(createEditWorkHoursPresenter(editWorkHoursView));
		view.setEditWorkHoursPresenter(editableWorkHoursList);
		return editableWorkHoursList;
	}

	public CreateWorkHoursPresenter createCreateWorkHoursPresenter(CreateWorkHoursView view) {
		CreateWorkHoursPresenter createPresenter = new CreateWorkHoursPresenter(view, DaoFactory.getEventBus());
		createPresenter.setDisplayCommandFactory(commandFactory);
		createPresenter.setCreateCommandFactory(commandFactory);
		createPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setCreatePresenter(createPresenter);
		return createPresenter;
	}

	public EditWorkHoursPresenter createEditWorkHoursPresenter(EditWorkHoursView view) {
		EditWorkHoursPresenter editPresenter = new EditWorkHoursPresenter(view, DaoFactory.getEventBus());
		editPresenter.setDisplayCommandFactory(commandFactory);
		editPresenter.setEditCommandFactory(commandFactory);
		editPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setEditPresenter(editPresenter);
		return editPresenter;
	}

	public WorkHoursListPresenter createWorkHoursListPresenter(WorkHoursListView view) {
		WorkHoursListPresenter workHoursListPresenter = new WorkHoursListPresenter(view, DaoFactory.getEventBus());
		workHoursListPresenter.setDisplayCommandFactory(commandFactory);
		workHoursListPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setWorkHoursListPresenter(workHoursListPresenter);
		return workHoursListPresenter;
	}

	public EntityCommandFactory<WorkHours> getCommandFactory() {
		return commandFactory;
	}
}
