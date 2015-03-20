package com.medicapital.client.core.commands;

import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Basic element command factory
 * 
 * @author mwronski
 * 
 * @param <E>
 */
public class EntityCommandFactory<E extends SerializableEntity> implements DisplayCommandFactory<E>, EditCommandFactory<E>, CreateCommandFactory<E> {

	private final Class<E> entityClass;
	private int entityId;
	private String login;
	private int userId;
	private SelectCommand<E> selectCommand;
	private SelectCountCommand<E> selectCountCommand;

	public EntityCommandFactory(final Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public SelectCommand<E> createSelectCommand() {
		if (selectCommand != null) {
			selectCommand.setLogin(login);
			selectCommand.setElementId(entityId);
			return selectCommand;
		}
		final SelectCommand<E> selectCommand = new SelectCommand<E>(entityClass, entityId);
		selectCommand.setLogin(login);
		selectCommand.setElementId(entityId);
		return selectCommand;
	}

	@Override
	public SelectCommand<E> createSelectCommand(final int startRow, final int rowsCount) {
		if (selectCommand != null) {
			selectCommand.setStartRow(startRow);
			selectCommand.setRowCount(rowsCount);
			return selectCommand;
		}
		return new SelectCommand<E>(entityClass, login, userId, startRow, rowsCount);
	}

	@Override
	public SelectCountCommand<E> createCountCommand() {
		if (selectCountCommand != null) {
			return selectCountCommand;
		}
		final SelectCountCommand<E> selectCountCommand = new SelectCountCommand<E>(entityClass, login);
		selectCountCommand.setElementId(entityId);
		return selectCountCommand;
	}

	@Override
	public DeleteCommand<E> createDeleteCommand(final int... elementIds) {
		if (elementIds == null || elementIds.length == 0) {
			return null;
		}
		final DeleteCommand<E> deleteCommand = new DeleteCommand<E>(entityClass, elementIds[0]);
		if (elementIds.length > 1) {
			for (int i = 1; i < elementIds.length; i++) {
				deleteCommand.addElementToDelete(elementIds[i]);
			}
		}
		return deleteCommand;
	}

	@Override
	public UpdateCommand<E> createUpdateCommand(final E element) {
		return new UpdateCommand<E>(entityClass, element);
	};

	@Override
	public CreateCommand<E> createCreateCommand(final E element) {
		return new CreateCommand<E>(entityClass, element);
	};

	@Override
	public void setEntityId(final int entityId) {
		this.entityId = entityId;
	}

	@Override
	public int getEntityId() {
		return entityId;
	}

	@Override
	public void setLogin(final String login) {
		this.login = login;
	}

	@Override
	public String getLogin() {
		return login;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setSelectCommand(final SelectCommand<E> selectCommand) {
		this.selectCommand = selectCommand;
	}

	public void setSelectCountCommand(final SelectCountCommand<E> selectCountCommand) {
		this.selectCountCommand = selectCountCommand;
	}

}
