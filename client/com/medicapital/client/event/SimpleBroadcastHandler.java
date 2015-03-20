package com.medicapital.client.event;

/**
 * Class accepts events from clients different then owner and handles it
 * properly.
 * 
 * @author mwronski
 * 
 */
abstract public class SimpleBroadcastHandler<T extends ClientEvent> implements ClientEventHandler {

	private final Object owner;

	/**
	 * Create handler to receive client events from senders different then owner
	 * 
	 * @param owner
	 */
	public SimpleBroadcastHandler(Object owner) {
		this.owner = owner;
	}

	/**
	 * Create handle broadcast client event handler
	 */
	public SimpleBroadcastHandler() {
		owner = null;
	}

	/**
	 * Handle client event
	 * 
	 * @param event
	 */
	@Override
	public void handle(final ClientEvent event) {
		if (owner != null && event.getSender() == owner) {
			return;
		}
		T castEvent = castEvent(event);
		if (castEvent != null) {
			handleEvent(castEvent);
		}
	}

	/**
	 * Cast event to proper type
	 * 
	 * @param event
	 * @return casted event or null when event can't be casted
	 */
	abstract protected T castEvent(ClientEvent event);

	/**
	 * Handle client event
	 * 
	 * @param event
	 */
	abstract protected void handleEvent(T event);

	/**
	 * Get owner of this handler
	 * 
	 * @return
	 */
	final protected Object getOwner() {
		return owner;
	}
}
