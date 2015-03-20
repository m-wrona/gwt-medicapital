package com.medicapital.client.ui;

final public class TestMessageWindow extends MessageWindow {

	private static int confirms = 0;
	private static int warnings = 0;

	public static void init() {
		UIUtil.setWindow(new TestMessageWindow());
	}

	public static void clear() {
		UIUtil.setWindow(null);
		warnings = 0;
		confirms = 0;
	}

	private TestMessageWindow() {
		// empty
	}

	@Override
	public void confirm(String message) {
		confirms++;
	}

	@Override
	public void alert(String message) {
		warnings++;
	}

	public static int getConfirms() {
		return confirms;
	}

	public static int getWarnings() {
		return warnings;
	}
}
