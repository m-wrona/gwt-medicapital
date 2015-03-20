package com.medicapital.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.common.util.Util;

/**
 * Class keeps generic features related with user interface.
 * 
 * @author mwronski
 * 
 */
public class UIUtil {

	// TODO remove this
	private static MessageWindow messageWindow;

	/**
	 * Show message on widget in tool tip form
	 * 
	 * @param msg
	 * @param widget
	 * @param autoHide
	 *            if true tool tip will be hidden automatically
	 */
	public static void showToolTip(String msg, Widget widget, boolean autoHide) {
		ToolTip toolTip = new ToolTip();
		toolTip.setMessage(msg);
		toolTip.showRelativeTo(widget, autoHide);
	}

	/**
	 * Show message on widget in tool tip form
	 * 
	 * @param msg
	 * @param widget
	 */
	public static void showToolTip(String msg, Widget widget) {
		showToolTip(msg, widget, false);
	}

	public static void autoHideMessage(final Widget textBox) {
		Timer autoHideTimer = new Timer() {

			@Override
			public void run() {
				textBox.setVisible(false);
			}
		};
		autoHideTimer.schedule(SystemSettings.getAutoHideTime());
	}

	/**
	 * Show confirm window
	 * 
	 * @param message
	 */
	public static void confirm(String message) {
		if (!Util.isEmpty(message)) {
			getWindow().confirm(message);
		}
	}

	/**
	 * Show alert window
	 * 
	 * @param message
	 */
	public static void alert(String message) {
		if (!Util.isEmpty(message)) {
			getWindow().alert(message);
		}
	}

	/**
	 * Show error window
	 * 
	 * @param message
	 */
	public static void error(String message) {
		if (!Util.isEmpty(message)) {
			getWindow().alert(message);
		}
	}

	static MessageWindow getWindow() {
		if (messageWindow == null) {
			messageWindow = new MessageWindow();
		}
		return messageWindow;
	}

	static void setWindow(MessageWindow messageWindow) {
		UIUtil.messageWindow = messageWindow;
	}

	private UIUtil() {
		// empty
	}
}
