package com.medicapital.client.visit;

import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientVisit;

final class VisitViewBinder {

	PatientVisit getEntityFromView(final PatientVisit oldVisit, final GetterVisitView view) {
		if (oldVisit == null) {
			throw new IllegalArgumentException("Null source visit");
		} else if (oldVisit.getPatient() == null) {
			throw new IllegalArgumentException("User not set");
		} else if (oldVisit.getDoctor() == null) {
			throw new IllegalArgumentException("Doctor not set");
		}

		final PatientVisit visit = new PatientVisit();
		visit.setId(oldVisit.getId());
		visit.setDoctor(oldVisit.getDoctor());
		visit.setPatient(oldVisit.getPatient());
		visit.setTitle(view.getVisitTitle());
		visit.setDescription(view.getNotes());
		visit.setStartTime(view.getBeginDate());
		visit.setEndTime(view.getEndDate());
		final Localization localization = new Localization();
		if (oldVisit.getLocalization() != null) {
			localization.setId(oldVisit.getLocalization().getId());
		}
		localization.setAddress(view.getAdress());
		localization.setCityId(view.getCityId());
		visit.setLocalization(localization);
		return visit;
	}

	void displayEntityOnView(final SetterVisitView view, final PatientVisit entity) {
		view.setVisitTitle(entity.getTitle());
		view.setNotes(entity.getDescription());
		view.setBeginDate(entity.getStartTime());
		view.setEndDate(entity.getEndTime());
		view.setDoctor(entity.getDoctor().getUser().getFirstName(), entity.getDoctor().getUser().getLastName());
		view.setAddress(entity.getLocalization().getAddress());
		view.setCityId(entity.getLocalization().getCityId());
		if (entity.getPatient() != null) {
			view.setPatient(entity.getPatient().getFirstName(), entity.getPatient().getLastName());
		}
	}

	void clearView(final SetterVisitView view) {
		view.setVisitTitle("");
		view.setNotes("");
		view.setBeginDate(null);
		view.setEndDate(null);
		view.setDoctor("", "");
		view.setAddress("");
		view.setCityId(-1);
		view.setPatient(null, null);
	}
}
