package com.medicapital.common.util;

import java.util.Collection;
import java.util.Map;

final public class Util {

	public static <T> void assertNotEmpty(T[] array, String msg) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException(msg);
		}
	}

	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(final Collection<?> collection) {
		return collection != null ? collection.isEmpty() : true;
	}

	public static boolean isEmpty(final Map<?, ?> map) {
		return map != null ? map.isEmpty() : true;
	}

	public static boolean isEmpty(final String text) {
		return text != null ? text.isEmpty() : true;
	}

	private Util() {
		// empty
	}
}
