package com.medicapital.client.doctor.visit;

import com.medicapital.client.core.entity.EditEntityView;
import com.medicapital.common.entities.VisitType;

public interface EditVisitTypeView extends SetterVisitTypeView, GetterVisitTypeView, EditEntityView<VisitType> {

	void setEditPresenter(EditVisitTypePresenter editVisitTypePresenter);
}
