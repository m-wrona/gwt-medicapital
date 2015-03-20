package com.medicapital.client.pages.menu;

import static com.medicapital.client.log.Tracer.tracer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.pages.PageNavigator;
import com.medicapital.client.pages.article.SearchArticleListPage;
import com.medicapital.client.pages.doctor.SearchDoctorPage;
import com.medicapital.client.pages.user.UserHomePage;
import com.medicapital.client.pages.user.UserProfilePage;
import com.medicapital.client.pages.visit.UserVisitListPage;
import com.medicapital.common.entities.LoginData;

/**
 * User menu panel where options for users, doctors etc. will be displayed.
 * 
 * @author michal
 * 
 */
public class UserMenu {

	private final EventBus eventBus;
	private final WidgetView view;
	private final LoginData loginData;

	UserMenu(final LoginData loginData, final EventBus eventBus, WidgetView view) {
		this.view = view;
		this.eventBus = eventBus;
		this.loginData = loginData;
	}

	final public void userProfile() {
		tracer(this).debug("User menu clicked: user profile");
		PageNavigator.getInstance().goToPage(UserProfilePage.class);
	}

	public void homePage() {
		tracer(this).debug("User menu clicked: user home page");
		PageNavigator.getInstance().goToPage(UserHomePage.class);
	}

	public void calendar() {
		tracer(this).debug("User menu clicked: calendar");
		// TODO
		throw new UnsupportedOperationException();
	}

	final public void showVisitList() {
		tracer(this).debug("User menu clicked: patient visit list");
		PageNavigator.getInstance().goToPage(UserVisitListPage.class);
	}

	final public void createNewVisit() {
		tracer(this).debug("User menu clicked: create new visit");
		PageNavigator.getInstance().goToPage(SearchDoctorPage.class);
	}

	final public void searchArticles() {
		tracer(this).debug("User menu clicked: search articles");
		PageNavigator.getInstance().goToPage(SearchArticleListPage.class);
	}

	final public void medicalHistory() {
		tracer(this).debug("User menu clicked: medical history");
		// TODO
		throw new UnsupportedOperationException();
	}

	final public Widget getDisplay() {
		return view.asWidget();
	}

	final public EventBus getEventBus() {
		return eventBus;
	}

	final protected LoginData getLoginData() {
		return loginData;
	}

}
