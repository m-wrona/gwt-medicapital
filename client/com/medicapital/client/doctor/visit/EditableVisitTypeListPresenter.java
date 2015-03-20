package com.medicapital.client.doctor.visit;

import java.util.Collection;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entities.EditableEntitiesPresenter;
import com.medicapital.common.entities.VisitType;

public class EditableVisitTypeListPresenter extends EditableEntitiesPresenter<VisitType> {

	private final EditableVisitTypeListView view;
	private CreateVisitTypePresenter createVisitTypePresenter;
	private EditVisitTypePresenter editVisitTypePresenter;
	private int doctorId;

	public EditableVisitTypeListPresenter(EditableVisitTypeListView view, EventBus eventBus) {
		super(VisitType.class, view, eventBus);
		this.view = view;
	}

	@Override
	public void clearPresenter() {
		super.clearPresenter();
		if (createVisitTypePresenter != null) {
			createVisitTypePresenter.clearPresenter();
		}
		if (editVisitTypePresenter != null) {
			editVisitTypePresenter.clearPresenter();
		}
	}

	public void init(int doctorId) {
		this.doctorId = doctorId;
	}

	@Override
	protected void handleCreatedEntity(VisitType createdEntity) {
		super.handleCreatedEntity(createdEntity);
		refreshDisplay();
	}

	@Override
	protected void displayDataOnView(Collection<VisitType> data) {
		for (VisitType visitType : data) {
			view.display(visitType.getId(), visitType.getName(), visitType.getDuration(), visitType.getDescription());
		}
	}

	public void createVisitType() {
		if (createVisitTypePresenter == null) {
			throw new IllegalArgumentException("Create visit type presenter not set");
		} else if (doctorId <= 0) {
			throw new IllegalArgumentException("DoctorId not set");
		}
		VisitType newVisitType = new VisitType();
		newVisitType.setDoctorId(doctorId);
		createVisitTypePresenter.display(newVisitType);
	}

	public void editVisitType(int visitTypeId) {
		if (editVisitTypePresenter == null) {
			throw new IllegalArgumentException("Edit visit type presenter not set");
		} else if (!getDisplayedElements().containsKey(visitTypeId)) {
			throw new IllegalArgumentException("Visit type doesn't belong to presenter - visitTypeId: " + visitTypeId);
		}
		VisitType visitType = getDisplayedElements().get(visitTypeId);
		editVisitTypePresenter.display(visitType);
	}

	public void setCreateVisitTypePresenter(CreateVisitTypePresenter createVisitTypePresenter) {
		this.createVisitTypePresenter = createVisitTypePresenter;
	}

	public void setEditVisitTypePresenter(EditVisitTypePresenter editVisitTypePresenter) {
		this.editVisitTypePresenter = editVisitTypePresenter;
	}

}
