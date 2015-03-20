package com.medicapital.client.evaluation;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenterView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.RatingForm;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;

public class CreateVisitEvaluationForm extends PopupableComposite implements CreateVisitEvaluationPresenterView {

	interface MyUiBinder extends UiBinder<Widget, CreateVisitEvaluationForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private CreateVisitEvaluationPresenter createVisitEvaluationPresenter;

	@UiField
	PushButton buttonSave;
	@UiField
	PushButton buttonCloseWindow;
	@UiField
	TextBox evaluationDate;
	@UiField
	RatingForm evaluation;
	@UiField
	TextBox title;
	@UiField
	TextArea description;

	public CreateVisitEvaluationForm() {
		initWidget(uiBinder.createAndBindUi(this));
		setPushButtonCloseWindow(buttonCloseWindow);
	}

	@UiHandler("buttonSave")
	public void saveClick(ClickEvent event) {
		if (createVisitEvaluationPresenter != null) {
			createVisitEvaluationPresenter.create();
		}
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
		evaluation.setRating((int) visitEvaluation);
	}

	@Override
	public String getEvaluationTitle() {
		return title.getText();
	}

	@Override
	public String getDescription() {
		return description.getText();
	}

	@Override
	public Date getCreated() {
		return dateFormatter.parseDateAndTime(evaluationDate.getText());
	}

	@Override
	public float getEvaluation() {
		return evaluation.getRating();
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonSave.setEnabled(enabled);
	}

	public void setCreateVisitEvaluationPresenter(CreateVisitEvaluationPresenter createVisitEvaluationPresenter) {
		this.createVisitEvaluationPresenter = createVisitEvaluationPresenter;
	}

	public CreateVisitEvaluationPresenter getCreateVisitEvaluationPresenter() {
		return createVisitEvaluationPresenter;
	}
}
