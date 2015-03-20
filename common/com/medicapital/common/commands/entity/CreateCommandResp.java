package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

public class CreateCommandResp<E extends SerializableEntity> extends CommandResp<E> {

	private E createdEntity;
	private int createdEntityId = -1;

	/**
	 * Constructor for RPC communication
	 */
	protected CreateCommandResp() {

	}

	public CreateCommandResp(Class<E> objectClass, int createdEntityId) {
		super(objectClass);
		this.createdEntityId = createdEntityId;
	}

	public boolean wasCreated() {
		return createdEntityId > 0;
	}

	public void setCreatedEntity(E createdEntity) {
		this.createdEntity = createdEntity;
	}

	public E getCreatedEntity() {
		return createdEntity;
	}

	public int getCreatedEntityId() {
		return createdEntityId;
	}

	@Override
	public String toString() {
		return "[" + getClass() + ", createdEntityId=" + createdEntityId + "]";
	}
}
