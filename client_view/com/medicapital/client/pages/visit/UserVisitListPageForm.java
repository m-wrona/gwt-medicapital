package com.medicapital.client.pages.visit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.evaluation.CreateVisitEvaluationForm;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenterView;
import com.medicapital.client.evaluation.VisitEvaluationForm;
import com.medicapital.client.evaluation.VisitEvaluationPresenterView;
import com.medicapital.client.visit.EditVisitForm;
import com.medicapital.client.visit.EditVisitView;
import com.medicapital.client.visit.VisitForm;
import com.medicapital.client.visit.VisitListForm;
import com.medicapital.client.visit.VisitPresenterView;

public class UserVisitListPageForm extends Composite {

	private static UserVisitListPageFormUiBinder uiBinder = GWT.create(UserVisitListPageFormUiBinder.class);

	interface UserVisitListPageFormUiBinder extends UiBinder<Widget, UserVisitListPageForm> {
	}

	@UiField
	VisitListForm visitList;

	private final EditVisitView editVisitView = new EditVisitForm();
	private final VisitPresenterView visitDetailsView = new VisitForm();
	private final VisitEvaluationPresenterView evaluationView = new VisitEvaluationForm();
	private final CreateVisitEvaluationPresenterView createEvaluationView = new CreateVisitEvaluationForm();

	public UserVisitListPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
		editVisitView.setShowAsDialogBox(true);
		visitDetailsView.setShowAsDialogBox(true);
		evaluationView.setShowAsDialogBox(true);
		createEvaluationView.setShowAsDialogBox(true);
	}

	public VisitListForm getVisitList() {
		return visitList;
	}

	public VisitPresenterView getVisitDetailsView() {
		return visitDetailsView;
	}

	public EditVisitView getEditVisitView() {
		return editVisitView;
	}

	public VisitEvaluationPresenterView getEvaluationView() {
		return evaluationView;
	}

	public CreateVisitEvaluationPresenterView getCreateEvaluationView() {
		return createEvaluationView;
	}

}
