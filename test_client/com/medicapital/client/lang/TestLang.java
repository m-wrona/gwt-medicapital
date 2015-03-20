package com.medicapital.client.lang;

final public class TestLang {

	public static void init() {
		Lang.setGenericText(com.medicapital.server.lang.Lang.generic());
		Lang.setWarningText(com.medicapital.server.lang.Lang.warning());
	}

	public static void clear() {
		Lang.setGenericText(null);
		Lang.setWarningText(null);
	}

	private TestLang() {
		// empty
	}
}
