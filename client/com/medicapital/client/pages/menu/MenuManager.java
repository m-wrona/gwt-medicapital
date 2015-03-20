package com.medicapital.client.pages.menu;

import static com.medicapital.client.log.Tracer.tracer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.client.login.LoginEvent;
import com.medicapital.common.entities.LoginData;

/**
 * {@link MenuManager} is responsible for creating menus on page. It decides
 * when proper menu should be visible and when not.
 * 
 * @author mwronski
 * 
 */
final public class MenuManager {

	private final RootPanel userMenuPanel;
	private final EventBus eventBus;

	public MenuManager(final RootPanel mainServiceMenuPanel, final RootPanel userMenuPanel) {
		this.userMenuPanel = userMenuPanel;
		this.eventBus = DaoFactory.getEventBus();
		createMainServiceMenu(mainServiceMenuPanel);
		initEventBusHandlers(eventBus);

	}

	private void createMainServiceMenu(final RootPanel mainServiceMenuPanel) {
		tracer(this).debug("Creating main service menu");
		final MainMenuForm mainMenuForm = new MainMenuForm();
		final MainServiceMenu mainServiceMenu = new MainServiceMenu(mainMenuForm);
		mainMenuForm.setMainMenu(mainServiceMenu);
		mainServiceMenuPanel.add(mainServiceMenu.getDisplay());
	}

	private void loggedIn(final LoginData loginData) {
		tracer(this).debug("User logged in: " + loginData);
		userMenuPanel.clear();
		switch (loginData.getRole()) {
		case User:
			tracer(this).debug("Creating user menu");
			final UserMenuForm userMenuForm = new UserMenuForm();
			final UserMenu userMenu = new UserMenu(loginData, eventBus, userMenuForm);
			userMenuForm.setUserMenuPresenter(userMenu);
			userMenuPanel.add(userMenu.getDisplay());
			break;

		case Doctor:
			tracer(this).debug("Creating doctor menu");
			final DoctorMenuForm doctorMenuForm = new DoctorMenuForm();
			final DoctorMenu doctorMenu = new DoctorMenu(loginData, eventBus, doctorMenuForm);
			doctorMenuForm.setUserMenuPresenter(doctorMenu);
			doctorMenuForm.setDoctorMenuPresenter(doctorMenu);
			userMenuPanel.add(doctorMenu.getDisplay());
			break;

		default:
			tracer(this).error("Don't know how to create options for role: " + loginData.getRole());
			break;
		}
	}

	private void logout() {
		tracer(this).debug("User logged out - clearing user menu panel");
		userMenuPanel.clear();
	}

	private void initEventBusHandlers(final EventBus eventBus) {
		tracer(this).debug("Adding event bus handlers");
		eventBus.addHandler(ClientEvent.TYPE, new SimpleBroadcastHandler<LoginEvent>(this) {

			@Override
			protected void handleEvent(final LoginEvent loginEvent) {
				if (loginEvent.isLoggedIn()) {
					loggedIn(loginEvent.getLoginData());
				} else {
					logout();
				}

			}

			@Override
			protected LoginEvent castEvent(final ClientEvent event) {
				if (event instanceof LoginEvent) {
					return (LoginEvent) event;
				}
				return null;
			}

		});
	}
}
