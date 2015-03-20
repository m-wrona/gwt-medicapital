package com.medicapital.client.visit;

import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.common.entities.PatientVisit;

public interface CreateVisitPresenterView extends EditVisitDataView, CreateEntityView<PatientVisit> {

	void setCreateVisitPresenter(CreateVisitPresenter createVisitPresenter);

}
