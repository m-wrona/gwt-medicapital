package com.medicapital.client.doctor.visit;

import com.medicapital.common.entities.VisitType;

class VisitTypeViewBinder {

	protected VisitType getEntityFromView(GetterVisitTypeView view, VisitType oldEntity) {
		VisitType visitType = new VisitType();
		if (oldEntity != null) {
			visitType.setId(oldEntity.getId());
			visitType.setDoctorId(oldEntity.getDoctorId());
		}
		visitType.setName(view.getName());
		visitType.setDuration(view.getDuration());
		visitType.setDescription(view.getDescription());
		return visitType;
	}

	protected void displayEntityOnView(VisitType entity, SetterVisitTypeView view) {
		view.setName(entity.getName());
		view.setDuration(entity.getDuration());
		view.setDescription(entity.getDescription());
	}

	protected void clearView(SetterVisitTypeView view) {
		view.setName(null);
		view.setDuration(0);
		view.setDescription(null);
	}
}
