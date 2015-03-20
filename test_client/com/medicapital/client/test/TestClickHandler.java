package com.medicapital.client.test;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class TestClickHandler implements HasClickHandlers {

	private ClickHandler handler;

	@Override
	public void fireEvent(GwtEvent<?> event) {
		// ignore
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		this.handler = handler;
		return null;
	}

	public ClickHandler getHandler() {
		return handler;
	}
}
