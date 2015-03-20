package com.medicapital.client.evaluation;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.evaluation.VisitEvaluationsView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;

public class VisitEvaluationListForm extends PopupableComposite implements VisitEvaluationsView {

	private static VisitEvaluationListFormUiBinder uiBinder = GWT.create(VisitEvaluationListFormUiBinder.class);

	interface VisitEvaluationListFormUiBinder extends UiBinder<Widget, VisitEvaluationListForm> {
	}

	@UiField
	VerticalPanel listPanel;
	@UiField
	Button buttonFirst;
	@UiField
	Button buttonPrevious;
	@UiField
	HTMLPanel pageButtonPanel;
	@UiField
	Button buttonNext;
	@UiField
	Button buttonLast;

	private DateFormatter dateFormatter;

	public VisitEvaluationListForm() {
		initWidget(uiBinder.createAndBindUi(this));
		dateFormatter = DateFactory.createDateFormatter();
	}

	@Override
	public void addVisitEvaluation(Date created, String title, String description, float evaluation) {
		VisitEvaluationRow row = new VisitEvaluationRow();
		row.getCreated().setText(dateFormatter.toDateAndTimeString(created));
		row.getEvaluationTitle().setText(title);
		row.getDescription().setText(description);
		row.getEvaluation().setRating((int) evaluation);
		listPanel.add(row);
	}

	@Override
	public void setCurrentPageNumber(int currentPageNumber) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTotalPageNumber(int totalPageNumber) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setNextEnable(boolean enabled) {
		buttonNext.setEnabled(enabled);
	}

	@Override
	public void setPreviousEnable(boolean enabled) {
		buttonPrevious.setEnabled(enabled);
	}

	@Override
	public void setFirstEnable(boolean enabled) {
		buttonFirst.setEnabled(enabled);
	}

	@Override
	public void setLastEnable(boolean enabled) {
		buttonLast.setEnabled(enabled);
	}

	@Override
	public void clear() {
		listPanel.clear();
	}

}
