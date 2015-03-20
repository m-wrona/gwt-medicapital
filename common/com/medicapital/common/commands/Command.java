package com.medicapital.common.commands;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Class represents service command to be executed on server side NOTE: members
 * can't be final because of RPC serialization.
 * 
 * @author mwronski
 * 
 * @param <T>
 */
abstract class Command<T extends SerializableEntity> implements IsSerializable {

	private String entityClassName;

	protected Command(Class<T> entityClass) {
		entityClassName = entityClass.getName();
	}

	/**
	 * Constructor for RPC communication
	 */
	protected Command() {

	}

	final public String getEntityClassName() {
		return entityClassName;
	}

	@Override
	public String toString() {
		return "[" + getClass() + ", entityClassName=" + entityClassName + "]";
	}

}
