package com.medicapital.client.core.entities;

import static com.medicapital.client.log.Tracer.tracer;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.commands.EditCommandFactory;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Presenter allows to display list of elements and update them (delete and edit
 * actions are supported)
 * 
 * @author michal
 * 
 * @param <E>
 */
abstract public class EditableEntitiesPresenter<E extends SerializableEntity> extends EntitiesPresenter<E> {

	private final EditableEntitiesView<E> display;
	private EditCommandFactory<E> editCommandFactory;
	private final Set<Integer> selectedElements = new HashSet<Integer>();

	public EditableEntitiesPresenter(final Class<E> entityClass, final EditableEntitiesView<E> display, final EventBus eventBus) {
		super(entityClass, display, eventBus);
		this.display = display;
	}

	public void setEntitySelected(final int entityId, final boolean selected) {
		tracer(this).debug("Set entity selected - elementId: " + entityId + ", selected: " + selected);
		if (selected) {
			selectedElements.add(entityId);
		} else {
			selectedElements.remove(entityId);
		}
		display.setDeleteSelectedHandlerEnabled(!selectedElements.isEmpty());
	}

	/**
	 * Delete selected elements
	 * 
	 * @param elementsIds
	 */
	final public void deleteSelectedEntities() {
		tracer(this).debug("Deleting entities - size: " + selectedElements.size());
		validatePresenter();
		// TODO: optimize that
		final int elementsIds[] = new int[selectedElements.size()];
		int index = 0;
		for (final int selectedElementId : selectedElements) {
			elementsIds[index++] = selectedElementId;
		}
		final DeleteCommand<E> deleteCommand = editCommandFactory.createDeleteCommand(elementsIds);
		getServiceAccess().execute(deleteCommand, new ResponseHandler<E>() {

			/**
			 * @param response
			 */
			@Override
			public void handle(final CommandResp<E> response) {
				if (response instanceof DeleteCommandResp<?>) {
					final DeleteCommandResp<E> deleteResp = (DeleteCommandResp<E>) response;
					tracer(this).debug("Response from service, elements deleted: " + deleteResp.getCount() + "/" + selectedElements.size());
					if (deleteResp.getCount() == selectedElements.size()) {
						final Set<Integer> deletedEntitiesIds = new HashSet<Integer>();
						final int elementsOnPage = getDisplayedElements().size();
						for (final int deletedEntityId : selectedElements) {
							deletedEntitiesIds.add(deletedEntityId);
							getDisplayedElements().remove(deletedEntityId);
						}
						// broadcast message
						deleteResp.setDeletedElements(deletedEntitiesIds);
						getEventBus().fireEvent(new ServiceResponse<E>(this, deleteResp));
						tracer(this).debug("Elements deleted: " + deletedEntitiesIds.size() + ", elements on page: " + elementsOnPage);
						if (deletedEntitiesIds.size() == elementsOnPage) {
							/*
							 * if all elements from current page were deleted
							 * then new page must be loaded
							 */
							tracer(this).debug("All elements from page were deleted - going to previous page...");
							goToPage(getCurrentPageNumber() - 1);
						} else {
							refreshDisplay(false);
						}
					}
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				// ignore

			}
		});
	}

	@Override
	final public void refreshDisplay(final boolean redownloadData) throws IllegalArgumentException {
		super.refreshDisplay(redownloadData);
		selectedElements.clear();
		display.setSelectAllEntities(false);
		display.setDeleteSelectedHandlerEnabled(false);
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

}
