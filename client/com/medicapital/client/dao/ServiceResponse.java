package com.medicapital.client.dao;

import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Event indicates that a response has occured from service. Event is a
 * broadcast event type.
 * 
 * @author mwronski
 * 
 * @param <E>
 */
final public class ServiceResponse<E extends SerializableEntity> extends ClientEvent {

	private final CommandResp<E> commandResp;

	public ServiceResponse(Object sender, CommandResp<E> commandResp) {
		super(sender);
		this.commandResp = commandResp;
	}

	final public CommandResp<E> getCommandResp() {
		return commandResp;
	}

	final public String getEntityClassName() {
		return commandResp.getEntityClassName();
	}

}
