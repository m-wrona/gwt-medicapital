package com.medicapital.client.doctor;

import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.client.user.UserViewBinder;
import com.medicapital.common.entities.Doctor;

public final class DoctorViewBinder implements EntityViewBinder<Doctor> {

	private final UserViewBinder userBinder;
	private SetterDoctorDataView setterView;
	private GetterDoctorDataView getterView;

	public DoctorViewBinder(SetterDoctorDataView setterView, GetterDoctorDataView getterView) {
		this.setterView = setterView;
		this.getterView = getterView;
		userBinder = new UserViewBinder(setterView, getterView);
	}

	@Override
	public Doctor getEntityFromView(Doctor sourceEntity) {
		if (getterView == null) {
			return null;
		}
		final Doctor doctor = new Doctor();
		doctor.setUser(userBinder.getEntityFromView((sourceEntity != null ? sourceEntity.getUser() : null)));
		doctor.setWorkId(getterView.getWorkId());
		doctor.setAverageEvaluation(getterView.getAverageEvaluation());
		return doctor;
	}

	@Override
	public void display(Doctor entity) {
		if (setterView == null) {
			return;
		}
		userBinder.display(entity.getUser());
		setterView.setNotes(entity.getNotes());
		setterView.setAverageEvaluation(entity.getAverageEvaluation());
		setterView.setWorkId(entity.getWorkId());
	}

	@Override
	public void clear() {
		if (setterView == null) {
			return;
		}
		userBinder.clear();
		setterView.setNotes("");
		setterView.setAverageEvaluation(0);
		setterView.setWorkId("");
	}
}
