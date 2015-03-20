package com.medicapital.client.doctor.visit;

import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.common.entities.VisitType;

public interface CreateVisitTypeView extends SetterVisitTypeView, GetterVisitTypeView, CreateEntityView<VisitType> {

	void setCreatePresenter(CreateVisitTypePresenter createVisitTypePresenter);
}
