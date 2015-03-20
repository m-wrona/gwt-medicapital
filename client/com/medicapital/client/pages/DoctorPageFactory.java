package com.medicapital.client.pages;

import com.medicapital.client.pages.article.EditArticlePage;
import com.medicapital.client.pages.article.NewArticlePage;
import com.medicapital.client.pages.doctor.DoctorCalendarPage;
import com.medicapital.client.pages.doctor.DoctorHomePage;
import com.medicapital.client.pages.doctor.EditDoctorVisitTypesPage;
import com.medicapital.client.pages.doctor.EditDoctorWorkHoursPage;
import com.medicapital.client.pages.doctor.SearchDoctorPage;
import com.medicapital.client.pages.visit.DoctorVisitListPage;

final class DoctorPageFactory extends PageFactory {

	@Override
	<T extends Page<?>> Page<?> createPage(final Class<T> pageClass) {
		if (pageClass.equals(SearchDoctorPage.class)) {
			return new SearchDoctorPage();
		} else if (pageClass.equals(DoctorCalendarPage.class)) {
			return new DoctorCalendarPage();
		} else if (pageClass.equals(DoctorHomePage.class)) {
			return new DoctorHomePage();
		} else if (pageClass.equals(NewArticlePage.class)) {
			return new NewArticlePage();
		} else if (pageClass.equals(EditArticlePage.class)) {
			return new EditArticlePage();
		} else if (pageClass.equals(DoctorVisitListPage.class)) {
			return new DoctorVisitListPage();
		} else if (pageClass.equals(EditDoctorWorkHoursPage.class)) {
			return new EditDoctorWorkHoursPage();
		} else if (pageClass.equals(EditDoctorVisitTypesPage.class)) {
			return new EditDoctorVisitTypesPage();
		}
		return null;
	}

	@Override
	Page<?> createPage(final String pageName) {
		if (getPageName(SearchDoctorPage.class).equals(pageName)) {
			return new SearchDoctorPage();
		} else if (getPageName(DoctorCalendarPage.class).equals(pageName)) {
			return new DoctorCalendarPage();
		} else if (getPageName(DoctorHomePage.class).equals(pageName)) {
			return new DoctorHomePage();
		} else if (getPageName(NewArticlePage.class).equals(pageName)) {
			return new NewArticlePage();
		} else if (getPageName(EditArticlePage.class).equals(pageName)) {
			return new EditArticlePage();
		} else if (getPageName(DoctorVisitListPage.class).equals(pageName)) {
			return new DoctorVisitListPage();
		} else if (getPageName(EditDoctorWorkHoursPage.class).equals(pageName)) {
			return new EditDoctorWorkHoursPage();
		} else if (getPageName(EditDoctorVisitTypesPage.class).equals(pageName)) {
			return new EditDoctorVisitTypesPage();
		}
		return null;
	}

}
