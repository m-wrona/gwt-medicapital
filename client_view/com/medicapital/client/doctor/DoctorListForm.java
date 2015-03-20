package com.medicapital.client.doctor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.doctor.SearchDoctorsView;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.entities.Doctor;

public class DoctorListForm extends DataTable<DoctorHeaderForm, DoctorRowForm, Doctor> implements SearchDoctorsView {

	@Override
	protected DoctorHeaderForm createHeader() {
		return new DoctorHeaderForm();
	}

	@Override
	public void addDoctor(int doctorId, String lastName, String firstName, float averageEvaluation) {
		DoctorRowForm row = new DoctorRowForm();
		row.getLastName().setText(lastName);
		row.getFirstName().setText(firstName);
		row.getEvaluation().setText("" + averageEvaluation);
		addRow(doctorId, row);
	}

	@Override
	public HasClickHandlers getSearchClickHandler() {
		return getHeader().getSearchForm().getButtonSearch();
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return getHeader().getSearchForm().getButtonCancel();
	}

	@Override
	public HasClickHandlers getDisplayDetailClickHandler(int entityId) {
		return getRowIdRowMap().get(entityId).getButtonDetails();
	}

	@Override
	public SearchDoctorCriteriaView getSearchCriteria() {
	    return getHeader().getSearchForm();
	}

}
