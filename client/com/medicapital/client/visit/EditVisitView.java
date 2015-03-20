package com.medicapital.client.visit;

import com.medicapital.client.core.entity.EditEntityView;
import com.medicapital.common.entities.PatientVisit;

/**
 * Interface enables to display and edit patient visit.
 * 
 * @author michal
 * 
 */
public interface EditVisitView extends EditVisitDataView, EditEntityView<PatientVisit> {

	void setEditVisitPresenter(EditVisitPresenter editVisitPresenter);

}
