package com.medicapital.client.pages.doctor;

import com.medicapital.client.doctor.visit.EditableVisitTypeListPresenter;
import com.medicapital.client.doctor.visit.VisitTypeFactory;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;

public class EditDoctorVisitTypesPage extends DoctorPage<EditDoctorVisitTypesPageForm> {

	private final VisitTypeFactory visitTypeFactory = new VisitTypeFactory();
	private EditableVisitTypeListPresenter visitTypeListPresenter;

	@Override
	protected EditDoctorVisitTypesPageForm createPageView() {
		return new EditDoctorVisitTypesPageForm();
	}

	@Override
	protected void initPage(EditDoctorVisitTypesPageForm pageView) {
		visitTypeFactory.getCommandFactory().setLogin(getLoggedUser());
		visitTypeFactory.getCommandFactory().setUserId(getLoggedUserId());
		visitTypeListPresenter = visitTypeFactory.createEditVisitTypeListPresenter(pageView.getVisitTypes(), pageView.getCreateVisitTypeView(), pageView.getEditVisitTypeView());
		visitTypeListPresenter.init(getLoggedUserId());
		visitTypeListPresenter.refreshDisplay();
		getPresenters().add(visitTypeListPresenter);
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, EditDoctorVisitTypesPage.class);
		return pageEvent;
	}

}
