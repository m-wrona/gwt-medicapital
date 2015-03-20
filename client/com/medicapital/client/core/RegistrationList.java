package com.medicapital.client.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * List allows to manage handler registrations and unregister them automatically
 * when needed
 * 
 * @author michal
 * 
 */
public class RegistrationList extends ArrayList<HandlerRegistration> {

	private static final long serialVersionUID = -2825597832422550680L;
	private Map<Object, List<HandlerRegistration>> handlerMap = new HashMap<Object, List<HandlerRegistration>>();

	/**
	 * Add register and bind it to key
	 * 
	 * @param handler
	 * @param key
	 */
	public void add(HandlerRegistration handler, Object key) {
		add(handler);
		List<HandlerRegistration> handlers = handlerMap.get(key);
		if (handlers == null) {
			handlers = new ArrayList<HandlerRegistration>();
			handlerMap.put(key, handlers);
		}
		handlers.add(handler);
	}

	/**
	 * Unregister handlers related with key
	 * 
	 * @param key
	 * @return
	 */
	public boolean unregister(Object key) {
		if (handlerMap.containsKey(key)) {
			for (HandlerRegistration handler : handlerMap.get(key)) {
				handler.removeHandler();
			}
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		for (HandlerRegistration registration : this) {
			if (registration != null) {
				registration.removeHandler();
			}
		}
		super.clear();
		handlerMap.clear();
	}

}
