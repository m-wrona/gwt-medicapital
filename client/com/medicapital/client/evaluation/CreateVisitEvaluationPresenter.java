package com.medicapital.client.evaluation;

import java.util.Date;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.common.entities.PatientEvaluation;

public class CreateVisitEvaluationPresenter extends CreateEntityPresenter<PatientEvaluation> {

	private final CreateVisitEvaluationPresenterView view;

	public CreateVisitEvaluationPresenter(CreateVisitEvaluationPresenterView display, EventBus eventBus) {
		super(PatientEvaluation.class, display, eventBus);
		view = display;
	}

	@Override
	protected PatientEvaluation getEntityFromView() {
		final PatientEvaluation newEvaluation = new VisitEvaluationViewBinder().getEntityFromView(getCurrentEntity(), view);
		newEvaluation.setCreated(new Date());
		return newEvaluation;
	}

	@Override
	protected void displayEntityOnView(PatientEvaluation entity) {
		new VisitEvaluationViewBinder().displayEntityOnView(entity, view);
	}

	@Override
	protected void clearView() {
		new VisitEvaluationViewBinder().clearView(view);
	}

}
