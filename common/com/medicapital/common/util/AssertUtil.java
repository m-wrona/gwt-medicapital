package com.medicapital.common.util;

public final class AssertUtil {

	public static void assertNotNull(Object o, String msg) {
		if (o == null) {
			throw new IllegalArgumentException(msg);
		}
	}

	private AssertUtil() {
		// no instances
	}
}
