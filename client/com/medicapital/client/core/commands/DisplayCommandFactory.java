package com.medicapital.client.core.commands;

import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Factory creates select commands for entity
 * 
 * @author mwronski
 * 
 * @param <E>
 */
public interface DisplayCommandFactory<E extends SerializableEntity> {

	/**
	 * Create select command
	 * 
	 * @return
	 */
	SelectCommand<E> createSelectCommand();

	/**
	 * Create select command for specified entities
	 * 
	 * @param startRow
	 * @param rowCount
	 * @return
	 */
	SelectCommand<E> createSelectCommand(int startRow, int rowsCount);

	/**
	 * Create select count command
	 * 
	 * @return
	 */
	SelectCountCommand<E> createCountCommand();

	/**
	 * Set entity ID which should be used while creating commands
	 * 
	 * @param entityId
	 */
	void setEntityId(int entityId);

	/**
	 * Get entityId which is used while creating commands
	 * 
	 * @return
	 */
	int getEntityId();

	/**
	 * Set user login which should be used while creating commands
	 * 
	 * @param userLogin
	 */
	void setLogin(String userLogin);

	/**
	 * Get login of a user for which commands are created
	 * 
	 * @return
	 */
	String getLogin();

}
