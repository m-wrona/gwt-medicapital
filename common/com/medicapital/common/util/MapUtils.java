package com.medicapital.common.util;

import static com.medicapital.common.util.Util.isEmpty;

import java.util.Iterator;
import java.util.Map;

final public class MapUtils {

	/**
	 * Get not null parameter's value from map
	 * 
	 * @param <T>
	 * @param requestParameters
	 * @param parameterName
	 * @param optional
	 * @return
	 * @throws IllegalArgumentException
	 *             when parameter is mandatory but was not found. When parameter
	 *             is mandatory and value is empty.
	 */
	public static String getString(final Map<?, ?> requestParameters, final String parameterName, final boolean optional) throws IllegalArgumentException {
		if (!optional && !requestParameters.containsKey(parameterName)) {
			throw new IllegalArgumentException("Parameter not found: " + parameterName);
		}
		final Object parameterValue = requestParameters.get(parameterName);
		final String stringParameterValue = parameterValue != null ? parameterValue.toString() : "";
		if (!optional && isEmpty(stringParameterValue)) {
			throw new IllegalArgumentException("Parameter " + requestParameters + " is null");
		}
		return stringParameterValue;
	}

	/**
	 * Get integer value of parameter.
	 * 
	 * @param requestParameters
	 * @param parameterName
	 * @param optional
	 * @return
	 * @throws IllegalArgumentException
	 *             when parameter is mandatory but was not found. When parameter
	 *             is mandatory and value couldn't be casted into integer.
	 */
	public static Integer getInt(final Map<?, ?> requestParameters, final String parameterName, final boolean optional) throws IllegalArgumentException {
		final String parameterValue = getString(requestParameters, parameterName, optional);
		if (optional && isEmpty(parameterValue)) {
			return null;
		}
		try {
			return Integer.valueOf(parameterValue);
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("Parameter '" + parameterName + "' with value '" + parameterValue + "' couldn't be casted into integer", e);
		}
	}

	/**
	 * Get integer value of parameter.
	 * 
	 * @param requestParameters
	 * @param parameterName
	 * @param defaultValue
	 *            if parameter is not set
	 * @return
	 */
	public static int getInt(final Map<?, ?> requestParameters, final String parameterName, int defaultValue) {
		Integer intValue = getInt(requestParameters, parameterName, true);
		return intValue != null ? intValue : defaultValue;
	}

	/**
	 * Get long value of parameter.
	 * 
	 * @param requestParameters
	 * @param parameterName
	 * @param optional
	 * @return
	 * @throws IllegalArgumentException
	 *             when parameter is mandatory but was not found. When parameter
	 *             is mandatory and value couldn't be casted into long.
	 */
	public static Long getLong(final Map<?, ?> requestParameters, final String parameterName, final boolean optional) throws IllegalArgumentException {
		final String parameterValue = getString(requestParameters, parameterName, optional);
		if (optional && isEmpty(parameterValue)) {
			return null;
		}
		try {
			return Long.valueOf(parameterValue);
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("Parameter '" + parameterName + "' with value '" + parameterValue + "' couldn't be casted into long", e);
		}
	}

	/**
	 * Get float value of parameter.
	 * 
	 * @param requestParameters
	 * @param parameterName
	 * @param optional
	 * @return
	 * @throws IllegalArgumentException
	 *             when parameter is mandatory but was not found. When parameter
	 *             is mandatory and value couldn't be casted into float.
	 */
	public static Float getFloat(final Map<?, ?> requestParameters, final String parameterName, final boolean optional) throws IllegalArgumentException {
		final String parameterValue = getString(requestParameters, parameterName, optional);
		if (optional && isEmpty(parameterValue)) {
			return null;
		}
		try {
			return Float.valueOf(parameterValue);
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("Parameter '" + parameterName + "' with value '" + parameterValue + "' couldn't be casted into float", e);
		}
	}

	public static String toString(final Iterator<?> iterator) {
		final StringBuilder string = new StringBuilder('[');
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				string.append(',');
			}
			string.append(iterator.next());
			first = false;
		}
		string.append(']');
		return string.toString();
	}

	public static String toString(final Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return "";
		}
		final StringBuilder string = new StringBuilder('[');
		for (final Map.Entry<?, ?> entry : map.entrySet()) {
			string.append(entry.getKey().toString()).append('=').append(entry.getValue());
			string.append(',');
		}
		string.setCharAt(string.length() - 1, ']');
		return string.toString();
	}

	private MapUtils() {
		// empty
	}
}
