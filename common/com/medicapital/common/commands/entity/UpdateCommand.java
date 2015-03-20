package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

public class UpdateCommand<E extends SerializableEntity> extends CommandReq<E> {

	private E elementToEdit;

	public UpdateCommand(Class<E> objectCass, E elementToEdit) {
		super(objectCass);
		this.elementToEdit = elementToEdit;
	}
	
	/**
	 * Constructor for RPC communication
	 */
	protected UpdateCommand() {

	}

	public E getElementToEdit() {
		return elementToEdit;
	}
	
	@Override
	public String toString() {
		return "[" + super.toString() + ", element to edit=" + elementToEdit + "]";
	}

}
