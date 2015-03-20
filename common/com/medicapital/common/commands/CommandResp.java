package com.medicapital.common.commands;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Command is a response from service after executing an action
 * 
 * @author mwronski
 * 
 * @param <E>
 */
abstract public class CommandResp<E extends SerializableEntity> extends Command<E> {

	protected CommandResp(Class<E> entityClass) {
		super(entityClass);
	}

	/**
	 * Constructor for RPC communication
	 */
	protected CommandResp() {

	}
}
