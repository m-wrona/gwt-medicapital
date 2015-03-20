package com.medicapital.client.login;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import com.medicapital.client.core.cookies.Cookie;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.util.Util;

final class SessionCookie {

	private static final String SESSION_COOKIE_NAME = "SessionCookie";
	private static final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private Cookie sessionCookie;
	private String sessionId;
	private Date sessionExpiryDate;
	private LoginData loginData;

	SessionCookie(String sessionId, Date sessionExpiryDate, LoginData loginData) {
		this.sessionId = sessionId;
		this.sessionExpiryDate = sessionExpiryDate;
		this.loginData = loginData;
		createSessionCookie();
	}

	SessionCookie() {
		sessionCookie = readSessionCookie();
	}

	private void createSessionCookie() {
		tracer(this).debug("Saving session data in cookie: " + SESSION_COOKIE_NAME);
		sessionCookie = new Cookie(SESSION_COOKIE_NAME);
		sessionCookie.setExpires(sessionExpiryDate);
		sessionCookie.setValues(sessionId, dateFormatter.toDateAndTimeString(sessionExpiryDate), "" + loginData.getId(), loginData.getLogin(), loginData.getRole().toString(), dateFormatter.toDateAndTimeString(loginData.getLastLoginDate()));
	}

	void setSessionExpiryDate(Date sessionExpiryDate) {
		this.sessionExpiryDate = sessionExpiryDate;
		createSessionCookie();
	}

	private Cookie readSessionCookie() {
		try {
			Cookie cookie = new Cookie(SESSION_COOKIE_NAME);
			String[] sessionValues = cookie.getValues();
			if (!Util.isEmpty(sessionValues)) {
				sessionId = sessionValues[0];
				sessionExpiryDate = dateFormatter.parseDateAndTime(sessionValues[1]);
				loginData = new LoginData();
				loginData.setId(Integer.parseInt(sessionValues[2]));
				loginData.setLogin(sessionValues[3]);
				loginData.setRole(UserRole.valueOf(sessionValues[4]));
				loginData.setLastLoginDate(dateFormatter.parseDateAndTime(sessionValues[5]));
				cookie.setExpires(sessionExpiryDate);
				tracer(this).debug("Last session data found in cookie: " + SESSION_COOKIE_NAME);
				return cookie;
			}
			return null;
		} catch (Throwable e) {
			tracer(this).error("Coudln't read session cookie - clearing", e);
			clear();
			return null;
		}
	}

	void clear() {
		tracer(this).debug("Clearing session cookie: " + SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			sessionCookie.clear();
		}
		sessionCookie = null;
		sessionId = null;
		sessionExpiryDate = null;
		loginData = null;
	}

	boolean isEmpty() {
		return sessionCookie == null;
	}

	/**
	 * Check whether session is valid
	 * 
	 * @return
	 */
	boolean isValid() {
		return !isEmpty() && sessionExpiryDate != null && new Date().before(sessionExpiryDate);
	}

	String getSessionId() {
		return sessionId;
	}

	Date getSessionExpiryDate() {
		return sessionExpiryDate;
	}

	LoginData getLoginData() {
		return loginData;
	}
}
