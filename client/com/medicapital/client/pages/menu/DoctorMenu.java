package com.medicapital.client.pages.menu;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.pages.article.NewArticlePage;
import com.medicapital.client.pages.article.DoctorArticlesPage;
import com.medicapital.client.pages.doctor.DoctorCalendarPage;
import com.medicapital.client.pages.doctor.DoctorHomePage;
import com.medicapital.client.pages.doctor.EditDoctorVisitTypesPage;
import com.medicapital.client.pages.doctor.EditDoctorWorkHoursPage;
import com.medicapital.client.pages.visit.DoctorVisitListPage;
import com.medicapital.common.entities.LoginData;

final public class DoctorMenu extends UserMenu {

	DoctorMenu(final LoginData loginData, final EventBus eventBus, WidgetView view) {
		super(loginData, eventBus, view);
	}

	@Override
	public void homePage() {
		tracer(this).debug("User menu clicked: doctor's home page");
		getEventBus().fireEvent(DoctorHomePage.createPageEvent(this, getLoginData().getId()));
	}

	@Override
	public void calendar() {
		tracer(this).debug("User menu clicked: doctor's calendar");
		getEventBus().fireEvent(DoctorCalendarPage.createPageEvent(this, getLoginData().getId()));
	}

	public void doctorArticles() {
		tracer(this).debug("User menu clicked: my articles");
		getEventBus().fireEvent(DoctorArticlesPage.createPageEvent(this));
	}

	public void addArticle() {
		tracer(this).debug("User menu clicked: add article");
		getEventBus().fireEvent(NewArticlePage.createPageEvent(this));
	}

	public void doctorVisits() {
		tracer(this).debug("User menu clicked: doctor visits");
		getEventBus().fireEvent(DoctorVisitListPage.createPageEvent(this));
	}

	public void editWorkHours() {
		tracer(this).debug("User menu clicked: edit doctor's work hours");
		getEventBus().fireEvent(EditDoctorWorkHoursPage.createPageEvent(this));
	}

	public void editVisitTypes() {
		tracer(this).debug("User menu clicked: edit doctor's visit types");
		getEventBus().fireEvent(EditDoctorVisitTypesPage.createPageEvent(this));
	}

	public void patientData() {
		tracer(this).debug("User menu clicked: patient data");
		// TODO
		throw new UnsupportedOperationException();
	}

}
