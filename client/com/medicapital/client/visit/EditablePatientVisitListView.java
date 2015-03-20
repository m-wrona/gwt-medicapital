package com.medicapital.client.visit;

import java.util.Date;

import com.medicapital.client.core.entities.EditableEntitiesView;
import com.medicapital.common.entities.PatientVisit;

/**
 * Interface displays list of visits of the patient
 * 
 * @author mwronski
 * 
 */
public interface EditablePatientVisitListView extends EditableEntitiesView<PatientVisit> {

	/**
	 * Add planned visit
	 * 
	 * @param visitId
	 * @param visitDate
	 * @param visitAddress
	 * @param doctorName
	 */
	void addPlannedVisit(int visitId, Date visitDate, String visitAddress, String doctorName);

	/**
	 * Add visit (this visit has already took place)
	 * 
	 * @param visitId
	 * @param visitDate
	 * @param visitAddress
	 * @param doctorName
	 * @param evaluationAdded
	 */
	void addVisit(int visitId, Date visitDate, String visitAddress, String doctorName, boolean evaluationAdded);

	/**
	 * Set whether evaluation button should be visible
	 * 
	 * @param visible
	 */
	void setEvaluationButtonVisible(boolean visible);

	/**
	 * Set presenter which controls this view
	 * 
	 * @param presenter
	 */
	void setPresenter(EditablePatientVisitListPresenter presenter);

}
