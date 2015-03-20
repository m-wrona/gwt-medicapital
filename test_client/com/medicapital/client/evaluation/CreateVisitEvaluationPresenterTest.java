package com.medicapital.client.evaluation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;

import com.medicapital.client.core.commands.CreateCommandFactory;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenterView;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.PatientEvaluation;

public class CreateVisitEvaluationPresenterTest {

	@Test
	public void testDisplayEvaluation() {
		CreateVisitEvaluationPresenterView view = mock(CreateVisitEvaluationPresenterView.class);
		CreateVisitEvaluationPresenter evaluationPresenter = new CreateVisitEvaluationPresenter(view, new TestEventBus());
		evaluationPresenter.display(new PatientEvaluation());
		verify(view).setViewVisible(true);
	}
	
	@SuppressWarnings("unchecked")
    @Test
	public void testCreateEvaluation() {
		CreateVisitEvaluationPresenterView view = mock(CreateVisitEvaluationPresenterView.class);
		CreateVisitEvaluationPresenter evaluationPresenter = new CreateVisitEvaluationPresenter(view, new TestEventBus());
		evaluationPresenter.display(new PatientEvaluation());
		verify(view).setViewVisible(true);
		evaluationPresenter.setServiceAccess(mock(ServiceAccess.class));
		evaluationPresenter.setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		evaluationPresenter.setCreateCommandFactory(mock(CreateCommandFactory.class));
		evaluationPresenter.create();
		verify(view).getEvaluationTitle();
	}
	
}
