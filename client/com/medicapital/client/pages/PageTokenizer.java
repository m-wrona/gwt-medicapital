package com.medicapital.client.pages;

import static com.medicapital.common.util.Util.*;
import static com.medicapital.client.log.Tracer.tracer;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.medicapital.common.util.MapUtils;

/**
 * URL tokenizer
 * 
 * @author mwronski
 * 
 */
final public class PageTokenizer implements PlaceTokenizer<Page<?>> {

	private static final char CONJUCTION_MARK = '/';
	private static final char EQUAL_MARK = '=';
	public static final String PLACE_TOKEN = "Page:";
	public static final String PAGE_NAME = "PageName";
	private static final String PARAMETER_SEPARATOR = "\\s*" + CONJUCTION_MARK + "\\s*";
	private static final String ASSIGMENT_SEPARATOR = "\\s*" + EQUAL_MARK + "\\s*";

	@Override
	final public Page<?> getPlace(final String token) {
		tracer(this).debug("Get place: " + token);
		final Map<String, String> params = getTokensParameters(token);
		final String pageName = MapUtils.getString(params, PAGE_NAME, false);
		params.remove(PAGE_NAME);
		final Page<?> page = PageNavigator.createPage(pageName);
		if (page == null) {
			tracer(this).debug("Page '" + pageName + "' wasn't created by page navigator - returning default page");
			final Page<?> defaultPage = PageNavigator.getDefaultPage();
			defaultPage.display();
			return defaultPage;
		}
		tracer(this).debug("Get place tokens:  pageName: " + pageName + ", page request parameters=" + params);
		page.addRequestParameters(params);
		page.display();
		return page;
	}

	/**
	 * Get parameters of token
	 * 
	 * @param token
	 * @return
	 */
	public Map<String, String> getTokensParameters(final String token) {
		final Map<String, String> tokenParams = new LinkedHashMap<String, String>();
		if (isEmpty(token)) {
			return tokenParams;
		}
		final String[] tokens = token.split(PARAMETER_SEPARATOR);
		for (final String tokenParameter : tokens) {
			final String[] values = tokenParameter.split(ASSIGMENT_SEPARATOR, 2);
			tokenParams.put(values[0], values[1]);
		}
		return tokenParams;
	}

	@Override
	final public String getToken(final Page<?> page) {
		tracer(this).debug("Get token for page: " + page.getClass());
		return getPageToken(page, page.getRequestParameters());
	}

	/**
	 * Get token of a page
	 * 
	 * @param page
	 * @param parameters
	 * @return
	 */
	final public String getPageToken(final Page<?> page, final Map<String, String> parameters) {
		final StringBuilder token = new StringBuilder();
		token.append(PAGE_NAME).append(EQUAL_MARK).append(page.getPageName());
		if (!isEmpty(parameters)) {
			token.append(CONJUCTION_MARK);
			token.append(toRequestParameterString(parameters));
		}
		tracer(this).debug("Page token: " + token);
		return token.toString();
	}

	/**
	 * Create string with request parameters
	 * 
	 * @param reqParameters
	 * @return
	 */
	private String toRequestParameterString(final Map<String, String> reqParameters) {
		final StringBuilder reqString = new StringBuilder();
		for (final Map.Entry<String, String> reqEnty : reqParameters.entrySet()) {
			reqString.append(reqEnty.getKey()).append(EQUAL_MARK).append(reqEnty.getValue());
			reqString.append(CONJUCTION_MARK);
		}
		if (!reqParameters.isEmpty()) {
			reqString.deleteCharAt(reqString.length() - 1);
		}
		return reqString.toString();
	}

}
