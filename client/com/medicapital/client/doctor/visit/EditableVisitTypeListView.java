package com.medicapital.client.doctor.visit;

import com.medicapital.client.core.entities.EditableEntitiesView;
import com.medicapital.common.entities.VisitType;

public interface EditableVisitTypeListView extends EditableEntitiesView<VisitType> {

	void display(int visitTypeId, String name, int duration, String description);

	void setEditablePresenter(EditableVisitTypeListPresenter visitTypeListPresenter);

}
