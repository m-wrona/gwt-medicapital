package com.medicapital.client.visit;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenterView;
import com.medicapital.client.evaluation.VisitEvaluationFactory;
import com.medicapital.client.evaluation.VisitEvaluationPresenterView;
import com.medicapital.client.user.SearchUserView;
import com.medicapital.client.user.UserFactory;
import com.medicapital.common.entities.PatientVisit;

final public class VisitFactory {

	private EntityCommandFactory<PatientVisit> commandFactory = new EntityCommandFactory<PatientVisit>(PatientVisit.class);

	public VisitPresenter createVisitPresenter(final VisitPresenterView view, final VisitEvaluationPresenterView evaluationView, final CreateVisitEvaluationPresenterView createEvaluationView) {
		final VisitPresenter visitPresenter = new VisitPresenter(view, DaoFactory.getEventBus());
		visitPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		visitPresenter.setDisplayCommandFactory(commandFactory);
		view.setVisitPresenter(visitPresenter);
		// add evaluation presenters
		final VisitEvaluationFactory evaluationFactory = new VisitEvaluationFactory();
		visitPresenter.setEvaluationPresenter(evaluationFactory.createEvaluationPresenter(evaluationView));
		visitPresenter.setCreateEvaluationPresenter(evaluationFactory.createCreateEvaluationPresenter(createEvaluationView));
		return visitPresenter;
	}

	public EditVisitPresenter createEditVisitPresenter(final EditVisitView view) {
		final EditVisitPresenter editVisitPresenter = new EditVisitPresenter(view, DaoFactory.getEventBus());
		editVisitPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		editVisitPresenter.setDisplayCommandFactory(commandFactory);
		editVisitPresenter.setEditCommandFactory(commandFactory);
		view.setEditVisitPresenter(editVisitPresenter);
		return editVisitPresenter;
	}

	public CreateVisitPresenter createCreateVisitPresenter(final CreateVisitPresenterView view, SearchUserView searchUserView) {
		final CreateVisitPresenter createVisitPresenter = new CreateVisitPresenter(view, DaoFactory.getEventBus());
		createVisitPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		createVisitPresenter.setDisplayCommandFactory(commandFactory);
		createVisitPresenter.setCreateCommandFactory(commandFactory);
		createVisitPresenter.setSearchPatientPresenter(new UserFactory().createSearchUserPresenter(searchUserView));
		view.setCreateVisitPresenter(createVisitPresenter);
		return createVisitPresenter;
	}

	public EditablePatientVisitListPresenter createVisitListPresenter(final EditablePatientVisitListView visitListView, final EditVisitView editVisitView, final VisitPresenterView visitDetailsView, final VisitEvaluationPresenterView evaluationView, final CreateVisitEvaluationPresenterView createEvaluationView) {
		final EditablePatientVisitListPresenter visitList = new EditablePatientVisitListPresenter(visitListView, DaoFactory.getEventBus());
		visitList.setServiceAccess(DaoFactory.getServiceAccess());
		visitList.setDisplayCommandFactory(commandFactory);
		visitList.setEditCommandFactory(commandFactory);
		visitListView.setPresenter(visitList);
		// add element presenters
		final VisitPresenter visitPresenter = createVisitPresenter(visitDetailsView, evaluationView, createEvaluationView);
		visitList.setEditVisitPresenter(createEditVisitPresenter(editVisitView));
		visitList.setVisitPresenter(visitPresenter);
		// add evaluation presenters
		visitList.setCreateEvaluationPresenter(visitPresenter.getCreateEvaluationPresenter());
		visitList.setEvaluationPresenter(visitPresenter.getEvaluationPresenter());
		return visitList;
	}

	public void setCommandFactory(final EntityCommandFactory<PatientVisit> commandFactory) {
		this.commandFactory = commandFactory;
	}

	public EntityCommandFactory<PatientVisit> getCommandFactory() {
		return commandFactory;
	}
}
