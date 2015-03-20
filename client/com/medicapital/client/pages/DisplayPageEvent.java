package com.medicapital.client.pages;

import java.util.LinkedHashMap;
import java.util.Map;

import com.medicapital.client.event.ClientEvent;

/**
 * Request event to change displayed page
 * 
 * @author mwronski
 * 
 */
final public class DisplayPageEvent extends ClientEvent {

	private final Class<? extends Page<?>> pageClass;
	private final Map<String, String> requestParameters = new LinkedHashMap<String, String>();

	/**
	 * Create display page instance
	 * 
	 * @param sender
	 * @param pageClass
	 */
	public <T extends Page<?>> DisplayPageEvent(Object sender, Class<T> pageClass) {
		super(sender);
		this.pageClass = pageClass;
	}

	/**
	 * Create display default page event instance
	 * 
	 * @param sender
	 */
	public DisplayPageEvent(Object sender) {
		super(sender);
		this.pageClass = null;
	}

	final public Class<? extends Page<?>> getPageClass() {
		return pageClass;
	}
	
	/**
	 * Add request parameter for the page
	 * 
	 * @param parameterName
	 * @param parameterValue
	 */
	final public void addRequestParameter(final String parameterName, final String parameterValue) {
		requestParameters.put(parameterName, parameterValue);
	}

	/**
	 * Add request parameters for the page
	 * 
	 * @param requestParameters
	 */
	final public void addRequestParameters(final Map<String, String> requestParameters) {
		this.requestParameters.putAll(requestParameters);
	}
	
	final public Map<String, String> getRequestParameters() {
	    return requestParameters;
    }
}
