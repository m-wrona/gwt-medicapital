package com.medicapital.client.evaluation;

import com.medicapital.common.entities.PatientEvaluation;

class VisitEvaluationViewBinder {

	PatientEvaluation getEntityFromView(PatientEvaluation oldEntity, CreateVisitEvaluationPresenterView view) {
		PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setTitle(view.getEvaluationTitle());
		evaluation.setDescription(view.getDescription());
		evaluation.setCreated(view.getCreated());
		evaluation.setEvaluation(view.getEvaluation());
		if (oldEntity != null) {
			evaluation.setId(oldEntity.getId());
			evaluation.setVisitId(oldEntity.getVisitId());
		}
		return evaluation;
	}

	void displayEntityOnView(PatientEvaluation entity, VisitEvaluationPresenterView view) {
		view.setEvaluationTitle(entity.getTitle());
		view.setDescription(entity.getDescription());
		view.setCreated(entity.getCreated());
		view.setEvaluation(entity.getEvaluation());
	}

	void clearView(VisitEvaluationPresenterView view) {
		view.setEvaluationTitle("");
		view.setDescription("");
		view.setCreated(null);
		view.setEvaluation(0);
	}
}
