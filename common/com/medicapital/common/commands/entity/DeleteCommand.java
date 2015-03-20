package com.medicapital.common.commands.entity;

import java.util.HashSet;
import java.util.Set;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

public class DeleteCommand<E extends SerializableEntity> extends CommandReq<E> {

	private Set<Integer> elementIds = new HashSet<Integer>();

	public DeleteCommand(Class<E> objectCass, int elementId) {
		super(objectCass);
		elementIds.add(elementId);
	}

	/**
	 * Constructor for RPC communication
	 */
	protected DeleteCommand() {

	}

	public void addElementToDelete(int elementId) {
		elementIds.add(elementId);
	}

	public Set<Integer> getElementIds() {
		return elementIds;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + ", elements IDs to delete=" + elementIds + "]";
	}

}
