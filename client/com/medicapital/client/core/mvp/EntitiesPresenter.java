package com.medicapital.client.core.mvp;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.PageablePresenter;
import com.medicapital.client.core.Presenter;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.entities.SerializableEntity;

public abstract class EntitiesPresenter<E extends SerializableEntity> extends PageablePresenter implements Presenter {

	private final EntitiesPresenterView<E> entitiesView;
	private final EventBus eventBus;
	private final Map<Integer, E> displayedElements = new LinkedHashMap<Integer, E>();
	private final RegistrationList listHandlers = new RegistrationList();
	private final RegistrationList pageHandlers = new RegistrationList();
	private final Class<E> entityClass;

	public EntitiesPresenter(Class<E> entityClass, final EntitiesPresenterView<E> display, final EventBus eventBus) {
		super(display);
		this.entitiesView = display;
		this.eventBus = eventBus;
		this.entityClass = entityClass;
		registerEventBusHandlers(eventBus, listHandlers);
		updateNavigationBar();
	}

	/**
	 * Register handlers in event bus
	 * 
	 * @param registrationList
	 * @param eventBus
	 */
	private void registerEventBusHandlers(final EventBus eventBus, final RegistrationList registrationList) {
		tracer(this).debug("Registering handlers for action events from event bus...");
		registrationList.add(eventBus.addHandler(ClientEvent.TYPE, new CommandRespBroadcastHandler<E>(this) {

			@Override
			protected void handleCreatedEntity(E createdEntity) {
				EntitiesPresenter.this.handleCreatedEntity(createdEntity);
			}

			@Override
			protected void handleDeletedEntities(Set<Integer> deletedEntitiesIds) {
				EntitiesPresenter.this.handleDeletedEntities(deletedEntitiesIds);
			}

			@Override
			protected void handleUpdatedEntity(E updatedEntity) {
				EntitiesPresenter.this.handleUpdatedEntity(updatedEntity);
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
	protected void handleCreatedEntity(E createdEntity) {
		// ignore
	}

	/**
	 * Handle deleted entities
	 * 
	 * @param deletedEntitiesIds
	 */
	private void handleDeletedEntities(Set<Integer> deletedEntitiesIds) {
		if (deletedEntitiesIds != null) {
			for (int deletedEntityId : deletedEntitiesIds) {
				if (displayedElements.containsKey(deletedEntityId)) {
					tracer(this).debug("Entity was deleted: " + deletedEntityId);
					displayedElements.remove(deletedEntityId);
					setTotalRows(getTotalRows() - 1);
				}
			}
			refreshDisplay(false);
		}
	}

	/**
	 * Handle updated entity
	 * 
	 * @param updatedEntity
	 */
	private void handleUpdatedEntity(E updatedEntity) {
		if (displayedElements.containsKey(updatedEntity.getId())) {
			tracer(this).debug("Entity was updated: " + updatedEntity);
			displayedElements.put(updatedEntity.getId(), updatedEntity);
			refreshDisplay(false);
		}
	}

	/**
	 * Display data in list
	 * 
	 * @param data
	 */
	public final void display(final Collection<E> data) {
		displayedElements.clear();
		entitiesView.setViewVisible(true);
		clearView();
		if (data != null) {
			tracer(this).debug("Displaying data, elements count: " + data.size());
			for (E element : data) {
				displayedElements.put(element.getId(), element);
			}
			for (E entity : data) {
				displayDataOnView(entity);
			}
		}
	}

	@Override
	protected final void displayCurrentPageData() {
		displayedElements.clear();
		clearView();
		getElements(getStartRow(), getPageSize());
	}

	/**
	 * Get elements from service access
	 * 
	 * @param startRow
	 * @param elementCount
	 */
	protected abstract void getElements(int startRow, int elementCount);

	/**
	 * Display data o view
	 * 
	 * @param entity
	 */
	protected abstract void displayDataOnView(final E entity);

	/**
	 * Clear view
	 */
	protected final void clearView() {
		tracer(this).debug("Clearing view");
		entitiesView.clear();
		pageHandlers.clear();
	}

	/**
	 * Clear state and handlers of the presenter
	 */
	@Override
	public final void clearPresenter() {
		tracer(this).debug("Clearing presenter...");
		listHandlers.clear();
		pageHandlers.clear();
	}

	/**
	 * Re-download data and refresh display
	 */
	public final void refreshDisplay() {
		refreshDisplay(true);
	}

	/**
	 * Refresh display
	 * 
	 * @param redownloadData
	 *            if true data will be get from server again. Otherwise only
	 *            data in list will be repainted
	 * @throws IllegalArgumentException
	 *             when re-download is true but no display command factory is
	 *             set
	 */
	public final void refreshDisplay(boolean redownloadData) throws IllegalArgumentException {
		tracer(this).debug("Refreshing display - redownloadingData=" + redownloadData);
		if (redownloadData) {
			displayCurrentPageData();
		} else {
			display(new ArrayList<E>(displayedElements.values()));
		}
	}

	final protected Map<Integer, E> getDisplayedElements() {
		return displayedElements;
	}

	final public Class<E> getEntityClass() {
		return entityClass;
	}

	final protected RegistrationList getPageHandlers() {
	    return pageHandlers;
    }

	final protected EventBus getEventBus() {
		return eventBus;
	}

	public final EntitiesPresenterView<E> getEntitiesView() {
		return entitiesView;
	}

}
