package com.medicapital.server.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.medicapital.server.log.Tracer;

final public class SessionFactory {

	private static LocalHttpSession httpSession;
	private boolean supportLocalSession = false;

	public HttpSession getSession(HttpServletRequest httpServletRequest) {
		Tracer.tracer(this).debug("Creating local session - supportLocalSession: " + supportLocalSession);
		return supportLocalSession ? getSessionInstance() : httpServletRequest.getSession();
	}

	private HttpSession getSessionInstance() {
		if (httpSession == null || !httpSession.isValid()) {
			Tracer.tracer(this).debug("Creating new local session");
			httpSession = new LocalHttpSession();
		}
		httpSession.refresh();
		return httpSession;
	}

	public void setSupportLocalSession(boolean supportLocalSession) {
		this.supportLocalSession = supportLocalSession;
	}
}
