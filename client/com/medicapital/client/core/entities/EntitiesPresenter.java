package com.medicapital.client.core.entities;

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
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Presenter helps to display data in dynamic table. It manages paging of the
 * table so next, previous, first and last actions are possible to handle. It
 * uses generic view interface which allows to display elements in proper
 * manner. View interface can be extended so new actions and features can be
 * added very easily.
 * 
 * @author michal
 * 
 * @param <E>
 *            E - element kept by presenter
 */
abstract public class EntitiesPresenter<E extends SerializableEntity> extends PageablePresenter implements Presenter {

	private final EntitiesView<E> display;
	private final EventBus eventBus;
	private final Map<Integer, E> displayedElements = new LinkedHashMap<Integer, E>();
	private final RegistrationList listHandlers = new RegistrationList();
	private final Class<E> entityClass;
	private ServiceAccess serviceAccess;
	private DisplayCommandFactory<E> displayCommandFactory;

	public EntitiesPresenter(Class<E> entityClass, final EntitiesView<E> display, final EventBus eventBus) {
		super(display);
		this.display = display;
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
	protected void registerEventBusHandlers(final EventBus eventBus, final RegistrationList registrationList) {
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
	protected void handleDeletedEntities(Set<Integer> deletedEntitiesIds) {
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
	protected void handleUpdatedEntity(E updatedEntity) {
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
	final public void display(final Collection<E> data) {
		displayedElements.clear();
		display.setViewVisible(true);
		clearView();
		if (data != null) {
			tracer(this).debug("Displaying data, elements count: " + data.size());
			for (E element : data) {
				displayedElements.put(element.getId(), element);
			}
			displayDataOnView(data);
		}
	}

	@Override
	protected void displayCurrentPageData() {
		validatePresenter();
		displayedElements.clear();
		clearView();
		getElements();
		getDataBaseMaxElementsCount();
	}

	/**
	 * Get elements from service access
	 */
	private void getElements() {
		tracer(this).debug("Getting elements: startRow: " + getStartRow() + ", rows count: " + getPageSize());
		SelectCommand<E> selectCommand = displayCommandFactory.createSelectCommand(getStartRow(), getPageSize());
		serviceAccess.execute(selectCommand, new ResponseHandler<E>() {

			@Override
			public void handle(CommandResp<E> response) {
				if (response instanceof SelectCommandResp) {
					SelectCommandResp<E> selectCommandResp = (SelectCommandResp<E>) response;
					display(selectCommandResp.getData());
				}
			}

			@Override
			public void handleException(Throwable throwable) {
				// ignore

			}
		});
	}

	/**
	 * Display data o view
	 * 
	 * @param data
	 */
	abstract protected void displayDataOnView(final Collection<E> data);

	/**
	 * Clear view
	 */
	protected void clearView() {
		tracer(this).debug("Clearing view");
		display.clear();
	}

	/**
	 * Check number of element in data base
	 */
	public void getDataBaseMaxElementsCount() {
		tracer(this).debug("Getting max data base elements");
		validatePresenter();
		SelectCountCommand<E> countCommand = displayCommandFactory.createCountCommand();
		serviceAccess.execute(countCommand, new ResponseHandler<E>() {

			@Override
			public void handle(CommandResp<E> response) {
				if (response instanceof SelectCountCommandResp) {
					SelectCountCommandResp<E> selectCommandResp = (SelectCountCommandResp<E>) response;
					setTotalRows(selectCommandResp.getCount());
				}
			}

			@Override
			public void handleException(Throwable throwable) {
				// ignore

			}
		});

	}

	/**
	 * Clear state and handlers of the presenter
	 */
	@Override
	public void clearPresenter() {
		tracer(this).debug("Clearing presenter...");
		listHandlers.clear();
	}

	/**
	 * Re-download data and refresh display
	 */
	final public void refreshDisplay() {
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
	public void refreshDisplay(boolean redownloadData) throws IllegalArgumentException {
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

	final public DisplayCommandFactory<E> getDisplayCommandFactory() {
		return displayCommandFactory;
	}

	final public void setDisplayCommandFactory(DisplayCommandFactory<E> displayCommandFactory) {
		this.displayCommandFactory = displayCommandFactory;
	}

	final public void setServiceAccess(ServiceAccess serviceAccess) {
		this.serviceAccess = serviceAccess;
	}

	final public ServiceAccess getServiceAccess() {
		return serviceAccess;
	}

	/**
	 * Validate state of presenter
	 * 
	 * @throws IllegalArgumentException
	 */
	protected void validatePresenter() throws IllegalArgumentException {
		tracer(this).debug("Validating presenter...");
		if (getServiceAccess() == null) {
			throw new IllegalArgumentException("Service access not set");
		} else if (displayCommandFactory == null) {
			throw new IllegalArgumentException("Display command factory not set");
		}
	}

	final public Class<E> getEntityClass() {
		return entityClass;
	}

	final protected RegistrationList getListHandlers() {
		return listHandlers;
	}

	final protected EventBus getEventBus() {
		return eventBus;
	}

	final public EntitiesView<E> getDisplay() {
		return display;
	}

}
