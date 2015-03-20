package com.medicapital.client.evaluation;

import java.util.Date;

import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.common.entities.PatientEvaluation;

public interface CreateVisitEvaluationPresenterView extends VisitEvaluationPresenterView, CreateEntityView<PatientEvaluation> {

	void setCreateVisitEvaluationPresenter(CreateVisitEvaluationPresenter createVisitEvaluationPresenter);
	
	String getEvaluationTitle();

	String getDescription();

	Date getCreated();

	float getEvaluation();

}
