package com.medicapital.client.evaluation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;

import com.medicapital.client.evaluation.VisitEvaluationPresenter;
import com.medicapital.client.evaluation.VisitEvaluationPresenterView;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.common.entities.PatientEvaluation;

public class VisitEvaluationPresenterTest {

	@Test
	public void testDisplayEvaluation() {
		VisitEvaluationPresenterView view = mock(VisitEvaluationPresenterView.class);
		VisitEvaluationPresenter evaluationPresenter = new VisitEvaluationPresenter(view, new TestEventBus());
		evaluationPresenter.display(new PatientEvaluation());
		verify(view).setViewVisible(true);
	}
}
