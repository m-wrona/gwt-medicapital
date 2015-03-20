package com.medicapital.client.evaluation;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.common.entities.PatientEvaluation;

public class VisitEvaluationPresenter extends EntityPresenter<PatientEvaluation> {

	private final VisitEvaluationPresenterView view;

	public VisitEvaluationPresenter(VisitEvaluationPresenterView entityView, EventBus eventBus) {
		super(PatientEvaluation.class, entityView, eventBus);
		view = entityView;
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
