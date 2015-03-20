package com.medicapital.server.access.url;

import static com.medicapital.server.log.Tracer.tracer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.medicapital.common.dao.UrlService;
import com.medicapital.common.util.MapUtils;
import com.medicapital.server.security.SessionData;
import com.medicapital.server.security.SessionFactory;

abstract class HttpController implements Controller {

	private com.medicapital.server.security.SecurityManager securityManager;
	private SessionFactory sessionFactory;

	@Override
	final public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			tracer(this).debug("Request received - parameters: " + MapUtils.toString(req.getParameterMap()));
			beforeRequestHandling(req);
			return handleHttpRequest(req, resp);
		} catch (Exception e) {
			tracer(this).error("HTTP controller error", e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, UrlService.STATUS_ERROR);
			throw e;
		}
	}

	/**
	 * handle HTTP request
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	abstract protected ModelAndView handleHttpRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception;

	private void beforeRequestHandling(HttpServletRequest req) {
		SessionData sessionData = getSessionData(req);
		securityManager.setSessionData(sessionData);
	}

	private SessionData getSessionData(HttpServletRequest req) {
		Object sessionData = getHttpSession(req).getAttribute(SessionData.SESSION_DATA);
		return sessionData != null ? (SessionData) sessionData : null;
	}

	/**
	 * Get session for current HTTP request
	 * 
	 * @return
	 */
	final protected HttpSession getHttpSession(HttpServletRequest req) {
		return sessionFactory.getSession(req);
	}

	final public void setSecurityManager(com.medicapital.server.security.SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	final public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
