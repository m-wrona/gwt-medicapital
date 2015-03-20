package com.medicapital.common.dao;

public interface UrlService {

	/**
	 * Time stamp which might be added to prevent caching of URL request
	 */
	String PARAM_TIMESTAMP = "ReqId";

	/**
	 * Status indicates that error occurred while performing action in URL
	 * access service
	 */
	String STATUS_ERROR = "ERROR";
}
