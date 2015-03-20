package com.medicapital.client.evaluation;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.PatientEvaluation;

final public class VisitEvaluationFactory {

	private EntityCommandFactory<PatientEvaluation> commandFactory = new EntityCommandFactory<PatientEvaluation>(PatientEvaluation.class);

	public VisitEvaluationPresenter createEvaluationPresenter(final VisitEvaluationPresenterView view) {
		final VisitEvaluationPresenter evaluationPresenter = new VisitEvaluationPresenter(view, DaoFactory.getEventBus());
		evaluationPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		evaluationPresenter.setDisplayCommandFactory(commandFactory);
		return evaluationPresenter;
	}

	public CreateVisitEvaluationPresenter createCreateEvaluationPresenter(final CreateVisitEvaluationPresenterView view) {
		final CreateVisitEvaluationPresenter evaluationPresenter = new CreateVisitEvaluationPresenter(view, DaoFactory.getEventBus());
		evaluationPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		evaluationPresenter.setDisplayCommandFactory(commandFactory);
		evaluationPresenter.setCreateCommandFactory(commandFactory);
		view.setCreateVisitEvaluationPresenter(evaluationPresenter);
		return evaluationPresenter;
	}

	public VisitEvaluationsPresenter createEvaluationsPresenter(final VisitEvaluationsView view) {
		final VisitEvaluationsPresenter evaluationsPresenter = new VisitEvaluationsPresenter(view, DaoFactory.getEventBus());
		evaluationsPresenter.setDisplayCommandFactory(commandFactory);
		evaluationsPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		return evaluationsPresenter;
	}

	public void setCommandFactory(final EntityCommandFactory<PatientEvaluation> commandFactory) {
		this.commandFactory = commandFactory;
	}

	public EntityCommandFactory<PatientEvaluation> getCommandFactory() {
		return commandFactory;
	}
}
