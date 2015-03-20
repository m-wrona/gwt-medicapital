package com.medicapital.client.core;

/**
 * Generic callback interface.
 * 
 * @param <T>
 *            the type of the expected result
 */
public class Callback<T> {
	
	/**
	 * Called when an operation completes normally.
	 * 
	 * @param result
	 *            the result of the operation
	 */
	public void onDone(T result) {
		// empty
	}

	/**
	 * Called when the task fails to complete.
	 * 
	 * @param t
	 *            the error thrown from the operation
	 */
	public void onError(Throwable t) {
		// empty
	}
}