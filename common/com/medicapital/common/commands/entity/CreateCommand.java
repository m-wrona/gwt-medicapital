package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

public class CreateCommand<E extends SerializableEntity> extends CommandReq<E> {

	private E elementToCreate;

	public CreateCommand(Class<E> objectCass, E elementToCreate) {
		super(objectCass);
		this.elementToCreate = elementToCreate;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected CreateCommand() {
	}

	public E getElementToCreate() {
		return elementToCreate;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + ", new element=" + elementToCreate + "]";
	}
}
