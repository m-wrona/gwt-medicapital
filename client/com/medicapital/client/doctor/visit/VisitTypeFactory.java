package com.medicapital.client.doctor.visit;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.VisitType;

public class VisitTypeFactory {

	private final EntityCommandFactory<VisitType> commandFactory = new EntityCommandFactory<VisitType>(VisitType.class);

	public EditableVisitTypeListPresenter createEditVisitTypeListPresenter(EditableVisitTypeListView view, CreateVisitTypeView createView, EditVisitTypeView editView) {
		EditableVisitTypeListPresenter listPresenter = new EditableVisitTypeListPresenter(view, DaoFactory.getEventBus());
		listPresenter.setDisplayCommandFactory(commandFactory);
		listPresenter.setEditCommandFactory(commandFactory);
		listPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		listPresenter.setCreateVisitTypePresenter(createCreateVisitTypePresenter(createView));
		listPresenter.setEditVisitTypePresenter(createEditVisitTypePresenter(editView));
		view.setEditablePresenter(listPresenter);
		return listPresenter;
	}

	public EditVisitTypePresenter createEditVisitTypePresenter(EditVisitTypeView view) {
		EditVisitTypePresenter editVisitTypePresenter = new EditVisitTypePresenter(view, DaoFactory.getEventBus());
		editVisitTypePresenter.setDisplayCommandFactory(commandFactory);
		editVisitTypePresenter.setEditCommandFactory(commandFactory);
		editVisitTypePresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setEditPresenter(editVisitTypePresenter);
		return editVisitTypePresenter;
	}

	public CreateVisitTypePresenter createCreateVisitTypePresenter(CreateVisitTypeView view) {
		CreateVisitTypePresenter createVisitTypePresenter = new CreateVisitTypePresenter(view, DaoFactory.getEventBus());
		createVisitTypePresenter.setDisplayCommandFactory(commandFactory);
		createVisitTypePresenter.setCreateCommandFactory(commandFactory);
		createVisitTypePresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setCreatePresenter(createVisitTypePresenter);
		return createVisitTypePresenter;
	}

	public EntityCommandFactory<VisitType> getCommandFactory() {
		return commandFactory;
	}

}
