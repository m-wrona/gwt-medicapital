package com.medicapital.client.login;

import static com.medicapital.common.util.Util.*;
import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.generic.RegisterPage;
import com.medicapital.client.pages.generic.RegisterPageForm;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.login.LoginCommand;
import com.medicapital.common.commands.login.LoginCommandResp;
import com.medicapital.common.commands.login.LogoutCommand;
import com.medicapital.common.commands.login.LogoutCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.LoginData;

/**
 * Class is responsible for authenticating users.
 * 
 * @author michal
 * 
 * @param <T>
 */
final public class LoginPresenter {

	final private RegistrationList registrationList = new RegistrationList();
	final private LoginView loginView;
	final private EventBus eventBus;
	final private ServiceAccess serviceAccess;
	final private LoggedUserView loggedUserView;
	final private SessionManager sessionManager;

	LoginPresenter(final LoginView loginView, final LoggedUserView loggedUserView, final EventBus eventBus, final ServiceAccess serviceAccess, final SessionManager sessionManager) {
		this.loginView = loginView;
		this.eventBus = eventBus;
		this.serviceAccess = serviceAccess;
		this.loggedUserView = loggedUserView;
		this.sessionManager = sessionManager;
		registerEventBusHandlers(eventBus, registrationList);
		if (sessionManager.isLoggedIn()) {
			setLoggedUserData(sessionManager.getLoginData());
		} else {
			loginView.setVisible(true);
			loggedUserView.setVisible(false);
		}
	}

	/**
	 * Register handlers for event bus events
	 * 
	 * @param eventBus
	 * @param registrationList
	 */
	protected void registerEventBusHandlers(final EventBus eventBus, final RegistrationList registrationList) {
		registrationList.add(eventBus.addHandler(ClientEvent.TYPE, new SimpleBroadcastHandler<LoginEvent>(this) {

			@Override
			protected void handleEvent(final LoginEvent loginEvent) {
				if (!loginEvent.isLoggedIn()) {
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
		}));
	}

	/**
	 * Clear state and handlers of this presenter
	 */
	final public void clearPresenter() {
		registrationList.clear();
	}

	/**
	 * Save login data
	 * 
	 * @param loginCommandResp
	 */
	private void saveLoginData(final LoginCommandResp loginCommandResp) {
		final LoginData loginData = loginCommandResp.getLoginData();
		tracer(this).debug("User logged successfully: " + loginData);
		sessionManager.createSession(loginCommandResp.getSessionId(), loginData, loginCommandResp.getSessionExpiryDate(), true);
		setLoggedUserData(loginData);
	}

	private void setLoggedUserData(LoginData loginData) {
		tracer(this).debug("Setting logged user data: " + loginData);
		setLoginFormButtonsEnabled(true);
		loginView.setWrongLoginDataMessageVisible(false);
		clearView();
		loginView.setVisible(false);
		loggedUserView.setLogin(loginData.getLogin());
		loggedUserView.setLastLoginDate(loginData.getLastLoginDate());
		loggedUserView.setVisible(true);
	}

	private void clearView() {
		loginView.setLogin("");
		loginView.setPassword("");
		loggedUserView.setLastLoginDate(null);
		loggedUserView.setLogin("");
	}

	/**
	 * Logout currently logged in user
	 */
	final public void logout() {
		tracer(this).debug("Logout user: " + sessionManager.getLoginData());
		if (sessionManager.getLoginData() != null) {
			loggedUserView.setLogoutButtonEnabled(false);
			serviceAccess.execute(new LogoutCommand(sessionManager.getLoginData().getLogin()), new ResponseHandler<LoginData>() {

				@Override
				public void handle(final CommandResp<LoginData> response) {
					if (response instanceof LogoutCommandResp) {
						LogoutCommandResp logoutCommandResp = (LogoutCommandResp) response;
						tracer(this).debug("Logout user: " + sessionManager.getLoginData() + " - is logout: " + logoutCommandResp.isLogout());
						loggedUserView.setLogoutButtonEnabled(true);
						if (logoutCommandResp.isLogout()) {
							clearView();
							loginView.setVisible(true);
							loggedUserView.setVisible(false);
							sessionManager.closeSession();
						}
					}
				}

				@Override
				public void handleException(final Throwable throwable) {
					loggedUserView.setLogoutButtonEnabled(true);
					// TODO show info here
				}

			});
		} else {
			clearView();
			loginView.setVisible(true);
			loggedUserView.setVisible(false);
		}
	}

	/**
	 * Login user
	 * 
	 */
	final public void login() {
		final String login = loginView.getLogin();
		final String password = loginView.getPassword();
		tracer(this).debug("Logging user with: " + login);
		loginView.setWrongLoginDataMessageVisible(false);
		loginView.setNoLoginPasswordMessageVisible(false);
		if (isEmpty(login) || isEmpty(password)) {
			loginView.setNoLoginPasswordMessageVisible(true);
			return;
		}
		setLoginFormButtonsEnabled(false);
		serviceAccess.execute(new LoginCommand(login, password), new ResponseHandler<LoginData>() {

			@Override
			public void handle(final CommandResp<LoginData> response) {
				if (response instanceof LoginCommandResp) {
					final LoginCommandResp loginResp = (LoginCommandResp) response;
					tracer(this).debug("User logged: " + loginResp.getLoginData());
					if (loginResp.getLoginData() != null) {
						saveLoginData(loginResp);
					} else {
						setLoginFormButtonsEnabled(true);
						loginView.setWrongLoginDataMessageVisible(true);
						clearView();
					}
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				loginView.setWrongLoginDataMessageVisible(true);
				setLoginFormButtonsEnabled(true);
			}

		});
	}

	/**
	 * Register new user in the service
	 */
	final public void registerNewUser() {
		tracer(this).debug("Registering new user - sending request to display page: " + RegisterPageForm.class);
		eventBus.fireEvent(new DisplayPageEvent(this, RegisterPage.class));
	}

	/**
	 * Set login form handlers enabled
	 * 
	 * @param enabled
	 */
	private void setLoginFormButtonsEnabled(final boolean enabled) {
		tracer(this).debug("Making login buttons enabled: " + enabled);
		loginView.setLoginHandlerEnabled(enabled);
		loginView.setForgotPasswordHandlerEnabled(enabled);
		loginView.setRegisterHandlerEnabled(enabled);
	}

	final public Widget getLoggedUserDisplay() {
		return loggedUserView.asWidget();
	}

	final public Widget getLoginDisplay() {
		return loginView.asWidget();
	}

}
