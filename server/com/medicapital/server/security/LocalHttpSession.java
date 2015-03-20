package com.medicapital.server.security;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * HTTPSession for local runtime
 * 
 * @author mwronski
 * 
 */
@SuppressWarnings("deprecation")
class LocalHttpSession implements HttpSession {

	private static final int MAX_INACTIVE_TIME = 30 * 60; // 30 minutes
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	private String sessionId;
	private Date creationTime = new Date();
	private Date lastAccessTime;

	public LocalHttpSession() {
		sessionId = UUID.randomUUID().toString();
	}

	/**
	 * Refresh session state
	 */
	void refresh() {
		if (lastAccessTime != null) {
			GregorianCalendar inactiveTimeCalendar = new GregorianCalendar();
			inactiveTimeCalendar.setTime(lastAccessTime);
			inactiveTimeCalendar.add(Calendar.SECOND, getMaxInactiveInterval());
			if (new Date().after(inactiveTimeCalendar.getTime())) {
				invalidate();
			}
		}
		if (sessionId != null) {
			lastAccessTime = new Date();
		}
	}

	boolean isValid() {
		return sessionId != null;
	}

	@Override
	public long getCreationTime() {
		return creationTime != null ? creationTime.getTime() : 0;
	}

	@Override
	public String getId() {
		return sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessTime != null ? lastAccessTime.getTime() : 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		return MAX_INACTIVE_TIME;
	}

	@Override
	public void invalidate() {
		creationTime = null;
		lastAccessTime = null;
		sessionId = null;
		attributes.clear();
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	public void removeAttribute(String key) {
		attributes.remove(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getValue(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getValueNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		throw new UnsupportedOperationException();
	}

}
