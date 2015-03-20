package com.medicapital.client.ui;

import com.google.gwt.user.client.Window;

class MessageWindow {

	/**
	 * Show confirm window
	 * 
	 * @param message
	 */
	public void confirm(String message) {
		Window.confirm(message);
	}

	/**
	 * Show alert window
	 * 
	 * @param message
	 */
	public void alert(String message) {
		Window.alert(message);
	}

	/**
	 * Show error window
	 * 
	 * @param message
	 */
	public void error(String message) {
		Window.alert(message);
	}
}
