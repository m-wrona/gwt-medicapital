package com.medicapital.client.evaluation;

import java.util.Date;

import com.medicapital.client.core.entity.EntityView;

public interface VisitEvaluationPresenterView extends EntityView {

	void setEvaluationTitle(String text);

	void setDescription(String text);

	void setCreated(Date date);

	void setEvaluation(float evaluation);
}
