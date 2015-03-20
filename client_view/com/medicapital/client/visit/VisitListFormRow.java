package com.medicapital.client.visit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

class VisitListFormRow extends TableRow {

	private static VisitListFormRowUiBinder uiBinder = GWT.create(VisitListFormRowUiBinder.class);

	interface VisitListFormRowUiBinder extends UiBinder<Widget, VisitListFormRow> {
	}

	@UiField 
	CheckBox selected;
	@UiField
	Label created;
	@UiField
	Label visitTitle;
	@UiField
	Label doctorName;
	@UiField
	Label localization;
	@UiField
	PushButton showDetails;
	@UiField
	PushButton showEvaluation;
	@UiField
	PushButton addEvaluation;

	public VisitListFormRow() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelected(selected);
	}

	public Label getCreated() {
		return created;
	}

	public Label getVisitTitle() {
		return visitTitle;
	}

	public Label getLocalization() {
		return localization;
	}

	public Label getDoctorName() {
		return doctorName;
	}

	public PushButton getShowDetails() {
		return showDetails;
	}

	public PushButton getShowEvaluation() {
		return showEvaluation;
	}

	public PushButton getAddEvaluation() {
		return addEvaluation;
	}

}
