package com.medicapital.client.dao;

import java.util.Set;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Response broadcast command handler
 * 
 * @author mwronski
 * 
 * @param <E>
 */
@SuppressWarnings("rawtypes")
abstract public class CommandRespBroadcastHandler<E extends SerializableEntity> extends SimpleBroadcastHandler<ServiceResponse> {

	/**
	 * Create command response handler for events sent by addresser different
	 * then owner
	 * 
	 * @param owner
	 */
	public CommandRespBroadcastHandler(Object owner) {
		super(owner);
	}

	@SuppressWarnings("unchecked")
	@Override
	final public void handleEvent(final ServiceResponse event) {
		if (getEntityClass() == null) {
			throw new IllegalArgumentException("Entity class not specified for handler");
		}
		final CommandResp<?> command = event.getCommandResp();
		if (acceptCommand(command)) {
			handle((CommandResp<E>) command);
		}
	}

	/**
	 * Check whether command should be handled
	 * 
	 * @param command
	 * @return
	 */
	protected boolean acceptCommand(CommandResp<?> command) {
		return command.getEntityClassName().equals(getEntityClass().getName());
	}

	@Override
	protected ServiceResponse castEvent(ClientEvent event) {
		if (event instanceof ServiceResponse) {
			return (ServiceResponse) event;
		}
		return null;
	}

	/**
	 * Handle response command
	 * 
	 * @param commandResp
	 */
	protected void handle(CommandResp<E> commandResp) {
		if (commandResp instanceof UpdateCommandResp) {
			handleUpdatedEntity(((UpdateCommandResp<E>) commandResp).getUpdatedEntity());
		} else if (commandResp instanceof CreateCommandResp) {
			handleCreatedEntity(((CreateCommandResp<E>) commandResp).getCreatedEntity());
		} else if (commandResp instanceof DeleteCommandResp) {
			handleDeletedEntities(((DeleteCommandResp<E>) commandResp).getDeletedElements());
		}
	}

	/**
	 * Handle updated entity
	 * 
	 * @param updatedEntity
	 */
	protected void handleUpdatedEntity(E updatedEntity) {
		// ignore
	}

	/**
	 * Handle created entity
	 * 
	 * @param createdEntity
	 */
	protected void handleCreatedEntity(E createdEntity) {
		// ignore
	}

	/**
	 * Handle deleted entities
	 * 
	 * @param deletedEntitiesIds
	 */
	protected void handleDeletedEntities(Set<Integer> deletedEntitiesIds) {
		// ignore
	}

	/**
	 * Get entity class which is expected by this handler
	 * 
	 * @return
	 */
	abstract protected Class<E> getEntityClass();

}
