package com.medicapital.client.pages.visit;

import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;
import com.medicapital.client.visit.EditablePatientVisitListPresenter;
import com.medicapital.client.visit.VisitFactory;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCommand;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCountCommand;

final public class DoctorVisitListPage extends DoctorPage<UserVisitListPageForm> {

	private final VisitFactory visitFactory = new VisitFactory();
	private EditablePatientVisitListPresenter visitListPresenter;

	@Override
	protected UserVisitListPageForm createPageView() {
		return new UserVisitListPageForm();
	}

	@Override
	protected void initPage(UserVisitListPageForm pageView) {
		visitListPresenter = visitFactory.createVisitListPresenter(pageView.getVisitList(), pageView.getEditVisitView(), pageView.getVisitDetailsView(), pageView.getEvaluationView(), pageView.getCreateEvaluationView());
		visitListPresenter.setCanAddEvaluations(false);
		visitFactory.getCommandFactory().setLogin(getLoggedUser());
		visitFactory.getCommandFactory().setSelectCommand(new SelectDoctorPatientVisitsCommand(getLoggedUserId()));
		visitFactory.getCommandFactory().setSelectCountCommand(new SelectDoctorPatientVisitsCountCommand(getLoggedUserId()));
		visitListPresenter.refreshDisplay();
		getPresenters().add(visitListPresenter);
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, DoctorVisitListPage.class);
		return pageEvent;
	}

}
