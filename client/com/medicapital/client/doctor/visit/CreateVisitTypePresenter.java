package com.medicapital.client.doctor.visit;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.common.entities.VisitType;

public class CreateVisitTypePresenter extends CreateEntityPresenter<VisitType> {

	private final CreateVisitTypeView view;

	public CreateVisitTypePresenter(CreateVisitTypeView view, EventBus eventBus) {
		super(VisitType.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected VisitType getEntityFromView() {
		return new VisitTypeViewBinder().getEntityFromView(view, getCurrentEntity());
	}

	@Override
	protected void displayEntityOnView(VisitType entity) {
		new VisitTypeViewBinder().displayEntityOnView(entity, view);
	}

	@Override
	protected void clearView() {
		new VisitTypeViewBinder().clearView(view);
	}

}
