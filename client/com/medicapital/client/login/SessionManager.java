package com.medicapital.client.login;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.LoginData;

/**
 * Class manages user's session on client side.
 * 
 * @author mwronski
 * 
 */
final public class SessionManager {

	private static SessionManager instacne;
	private final DateManager dateManager = DateFactory.createDateManager();
	private final EventBus eventBus = DaoFactory.getEventBus();
	private final Timer sessionTimer = new SessionTimer();
	private LoginData loginData;
	private String sessionId;
	private Date sessionExpiryDate;
	private SessionCookie sessionCookie;

	/**
	 * Create session
	 * 
	 * @param sessionId
	 * @param loginData
	 * @param sessionExpiryDate
	 * @param createCookie
	 *            store session data in cookie
	 * @throws IllegalStateException
	 *             session already exist
	 */
	void createSession(final String sessionId, final LoginData loginData, final Date sessionExpiryDate, boolean createCookie) throws IllegalStateException {
		if (this.sessionId != null) {
			throw new IllegalStateException("Session already exist");
		}
		tracer(this).debug("Creating session - sessionId: " + sessionId + ", expiry date: " + sessionExpiryDate + ", login data: " + loginData);
		this.sessionId = sessionId;
		this.loginData = loginData;
		this.sessionExpiryDate = sessionExpiryDate;
		sessionTimer.scheduleRepeating(SystemSettings.getSessionCheckTime());
		if (createCookie) {
			sessionCookie = new SessionCookie(sessionId, sessionExpiryDate, loginData);
		}
		// dispatch login event
		eventBus.fireEvent(new LoginEvent(this, loginData));
	}

	final public void setSessionExpiryDate(final Date sessionExpiryDate) {
		if (sessionId != null) {
			this.sessionExpiryDate = sessionExpiryDate;
			if (sessionCookie != null) {
				sessionCookie.setSessionExpiryDate(sessionExpiryDate);
			}
		}
	}

	/**
	 * Check session state
	 */
	private void checkSession() {
		if (sessionExpiryDate != null && dateManager.after(new Date(), sessionExpiryDate)) {
			tracer(this).debug("Session has expired");
			closeSession();
		}
	}

	/**
	 * Close session
	 */
	void closeSession() {
		if (sessionId != null) {
			tracer(this).debug("Closing session");
			sessionId = null;
			loginData = null;
			sessionExpiryDate = null;
			sessionTimer.cancel();
			if (sessionCookie != null) {
				sessionCookie.clear();
			}
			sessionCookie = null;
			// dispatch logout event
			eventBus.fireEvent(new LoginEvent(this));
		}
	}

	final public Date getSessionExpiryDate() {
		return new Date(sessionExpiryDate.getTime());
	}

	final public String getSessionId() {
		return sessionId;
	}

	/**
	 * Check whether current user is logged in
	 * 
	 * @return
	 */
	final public boolean isLoggedIn() {
		return sessionId != null;
	}

	public LoginData getLoginData() {
		return loginData != null ? loginData.clone() : null;
	}

	public static SessionManager getInstacne() {
		if (instacne == null) {
			instacne = new SessionManager();
		}
		return instacne;
	}

	private SessionManager() {
		sessionCookie = new SessionCookie();
		if (sessionCookie.isValid()) {
			tracer(this).debug("Restoring last session data from cookie");
			createSession(sessionCookie.getSessionId(), sessionCookie.getLoginData(), sessionCookie.getSessionExpiryDate(), false);
		} else {
			sessionCookie.clear();
			sessionCookie = null;
		}
	}

	/**
	 * Timer for checking session state
	 * 
	 * @author mwronski
	 * 
	 */
	private class SessionTimer extends Timer {

		@Override
		public void run() {
			checkSession();
		}

	}

}
