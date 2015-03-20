package com.medicapital.common.entities.search;

public class SearchDoctorCriteria extends SearchUserCriteria {

	private float evaluationFrom;
	private float evaluationTo;

	public float getEvaluationFrom() {
		return evaluationFrom;
	}

	public void setEvaluationFrom(float evaluationFrom) {
		this.evaluationFrom = evaluationFrom;
	}

	public float getEvaluationTo() {
		return evaluationTo;
	}

	public void setEvaluationTo(float evaluationTo) {
		this.evaluationTo = evaluationTo;
	}

}
