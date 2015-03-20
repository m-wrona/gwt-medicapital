package com.medicapital.client.evaluation;

import java.util.Collection;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entities.EntitiesPresenter;
import com.medicapital.common.entities.PatientEvaluation;

public class VisitEvaluationsPresenter extends EntitiesPresenter<PatientEvaluation> {

	private final VisitEvaluationsView view;

	public VisitEvaluationsPresenter(final VisitEvaluationsView view, final EventBus eventBus) {
		super(PatientEvaluation.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected void displayDataOnView(final Collection<PatientEvaluation> data) {
		for (final PatientEvaluation evaluation : data) {
			view.addVisitEvaluation(evaluation.getCreated(), evaluation.getTitle(), evaluation.getDescription(), evaluation.getEvaluation());
		}
	}

}
