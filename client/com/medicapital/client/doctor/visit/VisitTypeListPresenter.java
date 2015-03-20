package com.medicapital.client.doctor.visit;

import java.util.Collection;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entities.EntitiesPresenter;
import com.medicapital.common.entities.VisitType;

public class VisitTypeListPresenter extends EntitiesPresenter<VisitType> {

	private final VisitTypeListView view;

	public VisitTypeListPresenter(VisitTypeListView view, EventBus eventBus) {
		super(VisitType.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected void displayDataOnView(Collection<VisitType> data) {
		for (VisitType visitType : data) {
			view.display(visitType.getId(), visitType.getName(), visitType.getDuration());
		}
	}

}
