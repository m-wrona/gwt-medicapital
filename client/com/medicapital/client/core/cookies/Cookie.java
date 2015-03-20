package com.medicapital.client.core.cookies;

import java.util.Date;
import com.google.gwt.user.client.Cookies;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.util.Util;

/**
 * Class represents cookie
 * 
 * @author mwronski
 * 
 */
final public class Cookie {

	private final static String SEPARATOR = ";";
	private final static int DEFAULT_EXPIRY_TIME = 60; // minutes
	private final static String COOKIE_DOMAIN = SystemSettings.getServiceName();
	private final static String COOKIE_PATH = SystemSettings.getServiceName();
	private final String name;
	private final boolean secured;
	private String value;
	private Date expires;

	/**
	 * Create unsecured cookie's instance
	 * 
	 * @param name
	 */
	public Cookie(String name) {
		this(name, false);
	}

	/**
	 * Create cookie instance
	 * 
	 * @param name
	 * @param secured
	 */
	public Cookie(String name, boolean secured) {
		this.name = name;
		this.secured = secured;
	}

	/**
	 * Set values
	 * 
	 * @param values
	 */
	public void setValues(String... values) {
		Util.assertNotEmpty(values, "At least one value must be set");
		StringBuilder string = new StringBuilder();
		for (String value : values) {
			string.append(value).append(SEPARATOR);
		}
		// delete last separator
		string.deleteCharAt(string.length() - 1);
		setValue(string.toString());
	}

	/**
	 * Get cookie's values
	 * 
	 * @return
	 */
	public String[] getValues() {
		String value = getValue();
		return Util.isEmpty(value) ? null : value.split(SEPARATOR);
	}

	/**
	 * Set cookie's value
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		if (secured) {
			Cookies.setCookie(name, value, expires, COOKIE_DOMAIN, COOKIE_PATH, secured);
		} else if (expires != null) {
			Cookies.setCookie(name, value, expires);
		} else {
			Cookies.setCookie(name, value);
		}
		this.value = value;
	}

	/**
	 * Get cookie's value
	 * 
	 * @return
	 */
	public String getValue() {
		if (value == null) {
			value = Cookies.getCookie(name);
		}
		return value;
	}

	/**
	 * Set cookie's default expiration date
	 */
	public void setDefaultExpiryDate() {
		expires = new Date();
		DateFactory.createDateManager().addMinutes(expires, DEFAULT_EXPIRY_TIME);
	}

	/**
	 * Set cookie's expiration date
	 * 
	 * @param expires
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	/**
	 * Get cookie's expiration date
	 * 
	 * @return
	 */
	public Date getExpires() {
		return expires;
	}

	/**
	 * Is cookie secured
	 * 
	 * @return
	 */
	public boolean isSecured() {
		return secured;
	}

	/**
	 * Get cookie's name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Clear instance and remove cookie
	 */
	public void clear() {
		Cookies.removeCookie(name);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append("name=").append(getName());
		string.append(", value=").append(getValue());
		string.append(", secured=").append(isSecured());
		string.append(", expires=").append(getExpires());
		string.append("]");
		return string.toString();
	}
}
