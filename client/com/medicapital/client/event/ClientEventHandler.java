package com.medicapital.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Interface enables to receive client events.
 * 
 * @author mwronski
 * 
 */
public interface ClientEventHandler extends EventHandler {

	/**
	 * Handle client event
	 * 
	 * @param clientEvent
	 */
	void handle(ClientEvent clientEvent);
}
