package com.medicapital.client.config;

/**
 * System settings for client side
 * 
 * @author mwronski
 * 
 */
final public class SystemSettings {

	private static final int SECOND = 1000;
	private static final int MINUTE = 60 * SECOND;

	public static String getServiceName() {
		return "MediCapital";
	}

	/**
	 * Get number of elements which should be displayed in list on page
	 * 
	 * @return
	 */
	public static int getPageSize() {
		return 10;
	}

	/**
	 * Get number of URL resources which should be displayed in list
	 * 
	 * @return
	 */
	public static int getUrlResourceListCount() {
		return 5;
	}

	/**
	 * Get time in millis how often session should be checked
	 * 
	 * @return
	 */
	public static int getSessionCheckTime() {
		return 30 * SECOND;
	}

	/**
	 * Get auto-save time in millis
	 * 
	 * @return
	 */
	public static int getAutoSaveTime() {
		return 10 * MINUTE;
	}

	/**
	 * Get time after which messages should be hidden
	 * 
	 * @return
	 */
	public static int getAutoHideTime() {
		return 5 * SECOND;
	}

	private SystemSettings() {
		// empty
	}
}
