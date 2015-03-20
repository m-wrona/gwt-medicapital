package com.medicapital.client.doctor.work;

import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.WorkHours;

class WorkHoursViewBinder {

	WorkHours getEntityFromView(GetterWorkHoursView view, WorkHours oldEntity) {
		WorkHours workHours = new WorkHours();
		if (oldEntity != null) {
			workHours.setId(oldEntity.getId());
			workHours.setDoctorId(oldEntity.getDoctorId());
		}
		workHours.setDay(view.getDay());
		workHours.setDateFrom(view.getDateFrom());
		workHours.setDateTo(view.getDateTo());
		workHours.setStartHour(view.getStartHour());
		workHours.setStartMinutes(view.getStartMinutes());
		workHours.setEndHour(view.getEndHours());
		workHours.setEndMinutes(view.getEndMinutes());
		workHours.setDescription(view.getDescription());
		if (view.getCityId() > 0) {
			Localization localization = oldEntity.getLocalization() != null ? oldEntity.getLocalization() : new Localization();
			localization.setCityId(view.getCityId());
			localization.setAddress(view.getAddress());
			localization.setPostalCode(view.getPostalCode());
			workHours.setLocalization(localization);
		}
		return workHours;
	}

	void displayEntityOnView(SetterWorkHoursView view, WorkHours entity) {
		view.setDateFrom(entity.getDateFrom());
		view.setDateTo(entity.getDateTo());
		view.setDay(entity.getDay());
		view.setStartHour(entity.getStartHour());
		view.setStartMinutes(entity.getStartMinutes());
		view.setEndHour(entity.getEndHour());
		view.setEndMinutes(entity.getEndMinutes());
		view.setDescription(entity.getDescription());
		if (entity.getLocalization() != null) {
			view.setCityId(entity.getLocalization().getCityId());
			view.setAddress(entity.getLocalization().getAddress());
			view.setPostalCode(entity.getLocalization().getPostalCode());
		}
	}

	void clearView(SetterWorkHoursView view) {
		view.setDateFrom(null);
		view.setDateTo(null);
		view.setDay(null);
		view.setStartHour(0);
		view.setStartMinutes(0);
		view.setEndHour(0);
		view.setEndMinutes(0);
		view.setDescription(null);
		view.setCityId(-1);
		view.setAddress(null);
		view.setPostalCode(null);
	}
}
