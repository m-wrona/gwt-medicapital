package com.medicapital.client.core.entity;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.HashSet;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.medicapital.client.core.commands.EditCommandFactory;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.validation.EntityValidator;

/**
 * Presenter handles editing of element.
 * 
 * @author michal
 * 
 * @param <E>
 *            element type
 */
abstract public class EditEntityPresenter<E extends SerializableEntity> extends EntityPresenter<E> {

	private final EditEntityView<E> display;
	private EditCommandFactory<E> editCommandFactory;
	private EntityValidator<E> entityValidator;
	private Command afterEditCommand;
	private Command afterDeleteCommand;

	public EditEntityPresenter(final Class<E> entityClass, final EditEntityView<E> display, final EventBus eventBus) {
		super(entityClass, display, eventBus);
		this.display = display;
	}

	/**
	 * Get entity from view
	 * 
	 * @return
	 */
	abstract protected E getEntityFromView();

	/**
	 * Save edited element in service
	 * 
	 */
	final public void update() {
		final E element = getEntityFromView();
		tracer(this).debug("Updating element: " + element);
		validatePresenter();
		if (element == null || !validateElemenet(element)) {
			tracer(this).debug("Edited element is not valid - saving canceled");
			return;
		}
		display.setDeleteHandlerEnabled(false);
		display.setSaveHandlerEnabled(false);
		final UpdateCommand<E> updateCommand = editCommandFactory.createUpdateCommand(element);
		getServiceAccess().execute(updateCommand, new ResponseHandler<E>() {

			@Override
			public void handle(final CommandResp<E> response) {
				if (response instanceof UpdateCommandResp) {
					display.setDeleteHandlerEnabled(true);
					display.setSaveHandlerEnabled(true);
					UpdateCommandResp<E> updateCommandResp = (UpdateCommandResp<E>) response;
					afterEntityUpdated(element, updateCommandResp);
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				display.setDeleteHandlerEnabled(true);
				display.setSaveHandlerEnabled(true);
			}
		});

	}

	/**
	 * Perform activities after element was updated
	 * 
	 * @param updatedEntity
	 * @param updateCommandResp
	 */
	final private void afterEntityUpdated(E updatedEntity, UpdateCommandResp<E> updateCommandResp) {
		if (updateCommandResp.getCount() > 0) {
			updateCommandResp.setUpdatedEntity(updatedEntity);
			// broadcast change
			getEventBus().fireEvent(new ServiceResponse<E>(EditEntityPresenter.this, updateCommandResp));
			display(updatedEntity);
		}
		if (afterEditCommand != null) {
			afterEditCommand.execute();
		}
	}

	/**
	 * Check whether element is valid or not
	 * 
	 * @param element
	 * @return
	 */
	protected boolean validateElemenet(final E element) {
		return entityValidator != null ? entityValidator.validate(element) : true;
	}

	/**
	 * Delete element
	 * 
	 */
	final public void delete() {
		if (getCurrentEntity() == null) {
			return;
		}
		final int elementId = getCurrentEntity().getId();
		tracer(this).debug("Deleting element: " + elementId);
		validatePresenter();
		display.setDeleteHandlerEnabled(false);
		display.setSaveHandlerEnabled(false);
		final DeleteCommand<E> deleteCommand = editCommandFactory.createDeleteCommand(elementId);
		getServiceAccess().execute(deleteCommand, new ResponseHandler<E>() {

			@Override
			public void handle(final CommandResp<E> response) {
				if (response instanceof DeleteCommandResp) {
					display.setDeleteHandlerEnabled(true);
					display.setSaveHandlerEnabled(true);
					final DeleteCommandResp<E> deleteCommandResp = (DeleteCommandResp<E>) response;
					if (deleteCommandResp.getCount() > 0) {
						final Set<Integer> deletedElementsIds = new HashSet<Integer>();
						deletedElementsIds.add(elementId);
						deleteCommandResp.setDeletedElements(deletedElementsIds);
						// broadcast change
						getEventBus().fireEvent(new ServiceResponse<E>(EditEntityPresenter.this, deleteCommandResp));
						afterEntityDeleted();
					}
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				display.setDeleteHandlerEnabled(true);
				display.setSaveHandlerEnabled(true);
			}
		});
	}

	/**
	 * Perform activities after element was deleted
	 */
	final private void afterEntityDeleted() {
		display((E) null);
		if (display.isDialogBox()) {
			display.setViewVisible(false);
		}
		if (afterDeleteCommand != null) {
			afterDeleteCommand.execute();
		}
	}

	@Override
	protected void validatePresenter() throws IllegalArgumentException {
		super.validatePresenter();
		if (editCommandFactory == null) {
			throw new IllegalArgumentException("Edit command factory not set");
		}
	}

	final public void setEditCommandFactory(final EditCommandFactory<E> editCommandFactory) {
		this.editCommandFactory = editCommandFactory;
	}

	final public EditCommandFactory<E> getEditCommandFactory() {
		return editCommandFactory;
	}

	final public void setEntityValidator(final EntityValidator<E> entityValidator) {
		this.entityValidator = entityValidator;
	}

	final public EntityValidator<E> getEntityValidator() {
		return entityValidator;
	}

	/**
	 * Set command which should be executed after entity is deleted
	 * 
	 * @param afterDeleteCommand
	 */
	final public void setAfterDeleteCommand(Command afterDeleteCommand) {
		this.afterDeleteCommand = afterDeleteCommand;
	}

	/**
	 * Set command which should be executed after entity is edited
	 * 
	 * @param afterEditCommand
	 */
	final public void setAfterEditCommand(Command afterEditCommand) {
		this.afterEditCommand = afterEditCommand;
	}

}
