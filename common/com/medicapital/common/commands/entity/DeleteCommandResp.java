package com.medicapital.common.commands.entity;

import java.util.Set;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

public class DeleteCommandResp<E extends SerializableEntity> extends CommandResp<E> {

	private int count = 0;
	private Set<Integer> deletedElements;

	/**
	 * Constructor for RPC communication
	 */
	protected DeleteCommandResp() {

	}

	public DeleteCommandResp(Class<E> objectClass, int count) {
		super(objectClass);
		this.count = count;
	}

	public DeleteCommandResp(Class<E> objectClass) {
		super(objectClass);
	}

	public int getCount() {
		return count;
	}

	public void setDeletedElements(Set<Integer> deletedElements) {
		this.deletedElements = deletedElements;
	}

	public Set<Integer> getDeletedElements() {
		return deletedElements;
	}

	@Override
	public String toString() {
		return "[" + getClass() + ", count=" + count + "]";
	}
}
