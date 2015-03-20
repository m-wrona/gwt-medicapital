package com.medicapital.client.doctor;

import com.medicapital.client.core.mvp.SearchEntityPresenterView;
import com.medicapital.common.entities.Doctor;

public interface SearchDoctorsView extends SearchEntityPresenterView<Doctor> {

	void addDoctor(int doctorId, String lastName, String firstName, float averageEvaluation);

	SearchDoctorCriteriaView getSearchCriteria();

}
