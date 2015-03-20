package com.medicapital.client.core.mvp;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.Presenter;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.core.entity.EntityView;
import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.entities.SerializableEntity;

abstract public class AbstractPresenter<E extends SerializableEntity> implements Presenter {

	private final EventBus eventBus;
	private final RegistrationList registrationList = new RegistrationList();
	private final Class<E> entityClass;
	private EntityView entityView;
	private E currentEntity;

	public AbstractPresenter(final Class<E> entityClass, final EntityView entityView, final EventBus eventBus) {
		this.entityView = entityView;
		this.eventBus = eventBus;
		this.entityClass = entityClass;
		registerEventBusHandlers(eventBus, registrationList);
	}

	/**
	 * Clear state and handlers of presenter
	 */
	@Override
	public void clearPresenter() {
		tracer(this).debug("Clearing presenter...");
		registrationList.clear();
	}

	/**
	 * Register handlers in event bus
	 * 
	 * @param registrationList
	 * @param eventBus
	 */
	protected void registerEventBusHandlers(final EventBus eventBus, final RegistrationList registrationList) {
		tracer(this).debug("Registering handlers for action events from event bus...");
		registrationList.add(eventBus.addHandler(ClientEvent.TYPE, new CommandRespBroadcastHandler<E>(this) {

			@Override
			protected void handleCreatedEntity(final E createdEntity) {
				AbstractPresenter.this.handleCreatedEntity(createdEntity);
			}

			@Override
			protected void handleDeletedEntities(final Set<Integer> deletedEntitiesIds) {
				AbstractPresenter.this.handleDeletedEntities(deletedEntitiesIds);
			}

			@Override
			protected void handleUpdatedEntity(final E updatedEntity) {
				AbstractPresenter.this.handleUpdatedEntity(updatedEntity);
			}

			@Override
			protected Class<E> getEntityClass() {
				return entityClass;
			}

		}));
	}

	/**
	 * Handle created entity
	 * 
	 * @param createdEntity
	 */
	protected void handleCreatedEntity(final E createdEntity) {
		// ignore
	}

	/**
	 * Handle deleted entities
	 * 
	 * @param deletedEntitiesIds
	 */
	protected void handleDeletedEntities(final Set<Integer> deletedEntitiesIds) {
		if (currentEntity != null && deletedEntitiesIds != null && deletedEntitiesIds.contains(currentEntity.getId())) {
			tracer(this).debug("Element (id=" + currentEntity.getId() + ") was deleted: closing display...");
			currentEntity = null;
			clearView();
		}
	}

	/**
	 * Handle updated entity
	 * 
	 * @param updatedEntity
	 */
	protected void handleUpdatedEntity(final E updatedEntity) {
		if (currentEntity != null && updatedEntity.getId() == currentEntity.getId()) {
			tracer(this).debug("Element updated (id=" + updatedEntity.getId() + "): refreshing display...");
			display(updatedEntity);
		}
	}

	/**
	 * Display element
	 * 
	 * @param element
	 */
	public void display(final E element) {
		tracer(this).debug("Displaing element: " + element);
		entityView.setViewVisible(true);
		currentEntity = element;
		clearView();
		if (element != null) {
			displayEntityOnView(element);
		}
	}

	/**
	 * Display entity on view
	 * 
	 * @param entity
	 */
	abstract protected void displayEntityOnView(E entity);

	/**
	 * Clear the view
	 */
	abstract protected void clearView();

	/**
	 * Get data again from service and refresh display
	 */
	final public void refreshDisplay() {
		refreshDisplay(true);
	}

	/**
	 * Refresh display
	 * 
	 * @param redownloadData
	 *            if true data will be get from server again
	 */
	public void refreshDisplay(final boolean redownloadData) {
		// empty
	}

	final public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * Get current displayed element
	 * 
	 * @return
	 */
	final public E getCurrentEntity() {
		return currentEntity;
	}

	/**
	 * Get registration list
	 * 
	 * @return
	 */
	final protected RegistrationList getRegistrationList() {
		return registrationList;
	}

	final public void setEntityView(final EntityView entityView) {
		this.entityView = entityView;
	}

	final protected EventBus getEventBus() {
		return eventBus;
	}

	protected EntityView getDisplay() {
		return entityView;
	}

}
