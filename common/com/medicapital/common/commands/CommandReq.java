package com.medicapital.common.commands;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Command is a request for an action which should be taken on service side
 * 
 * @author mwronski
 * 
 * @param <E>
 */
abstract public class CommandReq<E extends SerializableEntity> extends Command<E> {

	protected CommandReq(Class<E> entityClass) {
		super(entityClass);
	}

	/**
	 * Constructor for RPC communication
	 */
	protected CommandReq() {

	}
	
}
