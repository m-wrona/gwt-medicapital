package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

public class SelectCountCommand<E extends SerializableEntity> extends CommandReq<E> {

	private String login;
	private int elementId = -1;
	private E selectCriteriaEntity;

	public SelectCountCommand(Class<E> objectCass) {
		super(objectCass);
	}

	public SelectCountCommand(Class<E> objectCass, E selectCriteriaEntity) {
		super(objectCass);
		this.selectCriteriaEntity = selectCriteriaEntity;
	}

	public SelectCountCommand(Class<E> objectCass, int elementId) {
		super(objectCass);
		this.elementId = elementId;
	}

	public SelectCountCommand(Class<E> objectCass, String login) {
		super(objectCass);
		this.login = login;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected SelectCountCommand() {

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public int getElementId() {
		return elementId;
	}

	public E getSelectCriteriaEntity() {
		return selectCriteriaEntity;
	}

	@Override
	public String toString() {
		return "[login=" + login + ", elementId=" + elementId + ", selectCriteriaEntity: " + selectCriteriaEntity + ", super info=" + super.toString() + "]";
	}

}
