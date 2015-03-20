package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

public class SelectCommand<E extends SerializableEntity> extends CommandReq<E> {

	private String login;
	private int userId;
	private int elementId = -1;
	private E selectCriteriaEntity;
	private int startRow = 0;
	private int rowCount = 10;

	/**
	 * Constructor for RPC communication
	 */
	protected SelectCommand() {

	}

	public SelectCommand(Class<E> objectCass, int elementId) {
		super(objectCass);
		this.elementId = elementId;
	}

	public SelectCommand(Class<E> objectCass) {
		super(objectCass);
	}

	public SelectCommand(Class<E> objectCass, String login, int userId, int startRow, int rowCount) {
		super(objectCass);
		this.login = login;
		this.userId = userId;
		this.startRow = startRow;
		this.rowCount = rowCount;
	}

	public SelectCommand(Class<E> objectCass, E selectCriteriaEntity) {
		super(objectCass);
		this.selectCriteriaEntity = selectCriteriaEntity;
	}

	public SelectCommand(Class<E> objectCass, E selectCriteriaEntity, int startRow, int rowCount) {
		super(objectCass);
		this.selectCriteriaEntity = selectCriteriaEntity;
		this.startRow = startRow;
		this.rowCount = rowCount;
	}

	public SelectCommand(Class<E> objectCass, String login) {
		super(objectCass);
		this.login = login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public int getElementId() {
		return elementId;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public E getSelectCriteriaEntity() {
		return selectCriteriaEntity;
	}

	@Override
	public String toString() {
		return "[login=" + login + ", elementId=" + elementId + ", startRow=" + startRow + ", rowCount=" + rowCount + ", selectCriteriaEntity: " + selectCriteriaEntity + ", super info=" + super.toString() + "]";
	}

}
