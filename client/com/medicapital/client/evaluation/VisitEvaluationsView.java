package com.medicapital.client.evaluation;

import java.util.Date;

import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.common.entities.PatientEvaluation;

public interface VisitEvaluationsView extends EntitiesView<PatientEvaluation> {

	void addVisitEvaluation(Date created, String title, String description, float evaluation);
}
