package com.medicapital.client.core;

import com.google.gwt.user.client.ui.Widget;

public interface WidgetView {

	/**
	 * Get view as widget
	 * 
	 * @return
	 */
	Widget asWidget();

	/**
	 * Set view visible
	 * 
	 * @param visible
	 */
	void setViewVisible(boolean visible);

	/**
	 * Show view as a dialog box
	 * 
	 * @param dialogBox
	 */
	void setShowAsDialogBox(boolean dialogBox);

	/**
	 * Check if view is displayed as dialog box
	 * 
	 * @return
	 */
	boolean isDialogBox();

	/**
	 * Set wheter view is blocked for user interactions
	 * 
	 * @param blocked
	 */
	void setViewBlocked(boolean blocked);
}
