package com.medicapital.client.doctor.visit;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.EditEntityPresenter;
import com.medicapital.common.entities.VisitType;

public class EditVisitTypePresenter extends EditEntityPresenter<VisitType> {

	private final EditVisitTypeView view;

	public EditVisitTypePresenter(EditVisitTypeView view, EventBus eventBus) {
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
