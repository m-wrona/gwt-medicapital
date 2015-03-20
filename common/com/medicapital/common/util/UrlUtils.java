package com.medicapital.common.util;

import java.util.Map;

final public class UrlUtils {

	/**
	 * Build request URL
	 * 
	 * @param url
	 *            basic URL
	 * @param params
	 *            request parameters if needed
	 * @return URL address
	 */
	public static String buildUrlRequest(final String url, final Map<?, ?> params) {
		final StringBuilder req = new StringBuilder(url);
		if (params == null || params.isEmpty()) {
			return req.toString();
		}
		req.append('?');
		for (final Map.Entry<?, ?> entry : params.entrySet()) {
			req.append(entry.getKey()).append('=').append(entry.getValue());
			req.append('&');
		}
		req.deleteCharAt(req.length() - 1);
		return req.toString();
	}

	private UrlUtils() {
		// empty
	}
}
