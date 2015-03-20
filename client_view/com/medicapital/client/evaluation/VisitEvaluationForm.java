package com.medicapital.client.evaluation;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.evaluation.VisitEvaluationPresenterView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;

public class VisitEvaluationForm extends PopupableComposite implements VisitEvaluationPresenterView {

	interface MyUiBinder extends UiBinder<Widget, VisitEvaluationForm> {
	}

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	PushButton buttonCloseWindow;
	@UiField
	TextBox evaluationDate;
	@UiField
	TextBox evaluation;
	@UiField
	TextBox title;
	@UiField
	TextArea description;

	public VisitEvaluationForm() {
		initWidget(uiBinder.createAndBindUi(this));
		setPushButtonCloseWindow(buttonCloseWindow);
		evaluationDate.setEnabled(false);
		evaluation.setEnabled(false);
		title.setEnabled(false);
		description.setEnabled(false);
	}

	@Override
	public void setEvaluationTitle(String text) {
		title.setText(text);
	}

	@Override
	public void setDescription(String text) {
		description.setText(text);
	}

	@Override
	public void setCreated(Date date) {
		evaluationDate.setText(dateFormatter.toDateAndTimeString(date));
	}

	@Override
	public void setEvaluation(float visitEvaluation) {
		evaluation.setText("" + visitEvaluation);
	}

}
