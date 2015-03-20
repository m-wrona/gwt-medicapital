package com.medicapital.client.pages.visit;

import com.medicapital.client.pages.UserPage;
import com.medicapital.client.visit.EditablePatientVisitListPresenter;
import com.medicapital.client.visit.VisitFactory;

final public class UserVisitListPage extends UserPage<UserVisitListPageForm> {

	public static final String PAGE_NAME = "PatientVisitList";

	private EditablePatientVisitListPresenter visitListPresenter;

	@Override
	protected UserVisitListPageForm createPageView() {
		return new UserVisitListPageForm();
	}

	@Override
	protected void initPage(UserVisitListPageForm pageView) {
		final VisitFactory visitFactory = new VisitFactory();
		visitFactory.getCommandFactory().setLogin(getLoggedUser());
		visitListPresenter = visitFactory.createVisitListPresenter(pageView.getVisitList(), pageView.getEditVisitView(), pageView.getVisitDetailsView(), pageView.getEvaluationView(), pageView.getCreateEvaluationView());
		visitListPresenter.refreshDisplay();
		getPresenters().add(visitListPresenter);
	}

}
