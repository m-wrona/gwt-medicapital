package com.medicapital.client.test;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.EventBus;

public class TestEventBus extends EventBus {

	private final EventBus eventBus = new SimpleEventBus();
	private final List<GwtEvent<?>> receivedEvents = new ArrayList<GwtEvent<?>>();

	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(Type<H> type, H handler) {
		return eventBus.addHandler(type, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(event);
		receivedEvents.add(event);
	}

	public List<GwtEvent<?>> getReceivedEvents() {
		return receivedEvents;
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addHandlerToSource(Type<H> type, Object source, H handler) {
		return eventBus.addHandlerToSource(type, source, handler);
	}

	@Override
	public void fireEventFromSource(GwtEvent<?> event, Object source) {
		eventBus.fireEventFromSource(event, source);
	}

}
