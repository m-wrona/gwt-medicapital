package com.medicapital.server.security;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;
import com.medicapital.common.entities.LoginData;

/**
 * Container for data related with user session
 * 
 * @author mwronski
 * 
 */
public class SessionData {

	public static final String SESSION_DATA = "SessionData";
	private HttpSession httpSession;
	private LoginData loginData;

	SessionData(HttpSession httpSession, final LoginData loginData) {
		this.httpSession = httpSession;
		this.loginData = loginData;
	}

	void invalidate() {
		if (httpSession != null) {
			httpSession.invalidate();
		}
		loginData = null;
		httpSession = null;
	}

	public Date getExpiryDate() {
		if (httpSession == null) {
			return null;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(httpSession.getLastAccessedTime());
		calendar.add(Calendar.SECOND, httpSession.getMaxInactiveInterval());
		return calendar.getTime();
	}

	HttpSession getHttpSession() {
		return httpSession;
	}

	public String getSessionId() {
		return httpSession != null ? httpSession.getId() : null;
	}

	public int getMaxInactiveInterval() {
		return httpSession != null ? httpSession.getMaxInactiveInterval() : 0;
	}

	public Date getCreationTime() {
		return httpSession != null ? new Date(httpSession.getCreationTime()) : null;
	}

	public Date getLastAccessedTime() {
		return httpSession != null ? new Date(httpSession.getLastAccessedTime()) : null;
	}

	public LoginData getLoginData() {
		return loginData != null ? loginData.clone() : null;
	}

	@Override
	public String toString() {
		final StringBuilder string = new StringBuilder("[");
		string.append("SessionId: ").append(getSessionId()).append(", ");
		string.append("Create time: ").append(getCreationTime()).append(", ");
		string.append("Max inactive interval: ").append(getMaxInactiveInterval()).append(", ");
		string.append("Last access time: ").append(getLastAccessedTime()).append(", ");
		string.append("Expiry date: ").append(getExpiryDate()).append(", ");
		string.append("Login data: ").append(loginData);
		string.append("]");
		return string.toString();
	}

}
