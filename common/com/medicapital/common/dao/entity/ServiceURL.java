package com.medicapital.common.dao.entity;

/**
 * Class contains addresses of proper server services
 * 
 * @author mwronski
 * 
 */
public final class ServiceURL {

	/**
	 * Base URL of all services
	 */
	public static final String BASE_URL = "GwtService/";
	public static final String USER_SERVICE = "UserService";
	public static final String DOCTOR_SERVICE = "DoctorService";

	private ServiceURL() {
		// no instance
	}
}
