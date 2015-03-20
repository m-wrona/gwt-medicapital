package com.medicapital.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Client side event
 * 
 * @author mwronski
 * 
 */
abstract public class ClientEvent extends GwtEvent<ClientEventHandler> {

	public static final Type<ClientEventHandler> TYPE = new Type<ClientEventHandler>();

	private final Object sender;

	public ClientEvent(Object sender) {
		this.sender = sender;
	}

	@Override
	final public Type<ClientEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	final protected void dispatch(final ClientEventHandler handler) {
		handler.handle(this);
	}

	/**
	 * Get sender of event
	 * 
	 * @return
	 */
	final public Object getSender() {
		return sender;
	}

}
