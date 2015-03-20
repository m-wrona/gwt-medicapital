package com.medicapital.client.evaluation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.generic.RatingForm;

class VisitEvaluationRow extends Composite {

	private static VisitEvaluationRowUiBinder uiBinder = GWT.create(VisitEvaluationRowUiBinder.class);

	interface VisitEvaluationRowUiBinder extends UiBinder<Widget, VisitEvaluationRow> {
	}

	@UiField
	Label created;
	@UiField
	Label title;
	@UiField
	Label description;
	@UiField
	RatingForm evaluation;

	public VisitEvaluationRow() {
		initWidget(uiBinder.createAndBindUi(this));
		evaluation.setEnabled(false);
	}

	public Label getCreated() {
		return created;
	}

	public Label getEvaluationTitle() {
		return title;
	}

	public Label getDescription() {
		return description;
	}

	public RatingForm getEvaluation() {
		return evaluation;
	}

}
