package com.medicapital.client.calendar;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.calendar.access.UserCalendarAccessController;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.calendar.SelectCalendarEventCommandResp;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.collections.WorkHoursMap;
import com.medicapital.common.util.Util;

/**
 * Presenters allows to control calendar view.
 * 
 * @author mwronski
 * 
 */
final public class CalendarPresenter {

	private final Map<Integer, CalendarEvent> eventMap = new HashMap<Integer, CalendarEvent>();
	private final CalendarView view;
	private final EventBus eventBus;
	private final CalendarEventDataDisplayer calendarEventDisplayer;
	private UserCalendarAccessController dataAccessController;
	private ServiceAccess serviceAccess;
	private CalendarCommandFactory commandFactory;

	public CalendarPresenter(final CalendarView view, final EventBus eventBus, final CalendarEventDataDisplayer calendarEventDisplayer) {
		this.view = view;
		this.eventBus = eventBus;
		this.calendarEventDisplayer = calendarEventDisplayer;
		initEventBusHandlers(eventBus);
	}

	private void initEventBusHandlers(final EventBus eventBus) {
		eventBus.addHandler(ClientEvent.TYPE, new CalendarEventBroadcastHandler(this) {

			@Override
			protected void handleCreatedEntity(final CalendarEvent createdEntity) {
				tracer(CalendarPresenter.this).debug("Event bus message - calendar event created: " + createdEntity);
				if (createdEntity != null) {
					eventMap.put(createdEntity.getId(), createdEntity);
					view.add(createdEntity);
				}
			}

			@Override
			protected void handleDeletedEntities(final Set<Integer> deletedEntitiesIds) {
				tracer(CalendarPresenter.this).debug("Event bus message - calendar events deleted size: " + deletedEntitiesIds.size());
				if (!Util.isEmpty(deletedEntitiesIds)) {
					final Set<Integer> toDelete = new HashSet<Integer>();
					for (final int eventId : deletedEntitiesIds) {
						if (eventMap.containsKey(eventId)) {
							view.remove(eventMap.get(eventId));
							toDelete.add(eventId);
						}
					}
					eventMap.keySet().removeAll(toDelete);
				}
			}

			@Override
			protected void handleUpdatedEntity(final CalendarEvent updatedEntity) {
				tracer(CalendarPresenter.this).debug("Event bus message - calendar events updated: " + updatedEntity);
				if (updatedEntity != null) {
					if (eventMap.containsKey(updatedEntity.getId())) {
						view.remove(eventMap.remove(updatedEntity.getId()));
						eventMap.put(updatedEntity.getId(), updatedEntity);
						view.add(updatedEntity);
					}
				}
			}

		});
	}

	/**
	 * Handle new event click
	 * 
	 * @param startTime
	 * @param endTime
	 */
	final public void addEventClick(final Date startTime, final Date endTime) {
		tracer(this).debug("Add event click - start time: " + startTime + ", end time: " + endTime);
		validate();
		if (dataAccessController.canAdd(startTime, endTime)) {
			calendarEventDisplayer.displayNewEventView(startTime, endTime);
		}
	}

	/**
	 * Handle edit event click
	 * 
	 * @param eventId
	 * @throws IllegalArgumentException
	 */
	final public void editEventClick(final int eventId) throws IllegalArgumentException {
		tracer(this).debug("Edit event click - eventId: " + eventId);
		validate();
		if (!eventMap.containsKey(eventId)) {
			throw new IllegalArgumentException("Event '" + eventId + "' is not displayed on current calendar");
		}
		final CalendarEvent event = eventMap.get(eventId);
		if (dataAccessController.canUpdate(event)) {
			calendarEventDisplayer.displayEditEventView(event);
		} else if (dataAccessController.canDisplay(event)) {
			calendarEventDisplayer.displayEventView(event);
		}
	}

	/**
	 * Add new event
	 * 
	 * @param termBeginDate
	 */
	final public <T extends CalendarEvent> void addEvent(final T event) {
		tracer(this).debug("Adding event: " + event);
		validate();
		view.setViewBlocked(true);
		if (dataAccessController.canAdd(event)) {
			final CreateCommand<CalendarEvent> createCommand = commandFactory.createCreateCommand(event);
			serviceAccess.execute(createCommand, new ResponseHandler<CalendarEvent>() {

				@Override
				public void handle(final CommandResp<CalendarEvent> response) {
					final CreateCommandResp<CalendarEvent> createCommandResp = (CreateCommandResp<CalendarEvent>) response;
					event.setId(createCommandResp.getCreatedEntityId());
					createCommandResp.setCreatedEntity(event);
					eventMap.put(event.getId(), event);
					view.add(event.cloneEvent());
					eventBus.fireEvent(new ServiceResponse<CalendarEvent>(CalendarPresenter.this, createCommandResp));
					view.setViewBlocked(false);
				}

				@Override
				public void handleException(final Throwable throwable) {
					// TODO Auto-generated method stub
					view.setViewBlocked(false);

				}

			});
		} else {
			// restore old view
			view.remove(event);
			view.setViewBlocked(false);
		}
	}

	/**
	 * Update event
	 * 
	 * @param termBeginDate
	 */
	final public <T extends CalendarEvent> void updateEvent(final T event) {
		tracer(this).debug("Updating event: " + event);
		validate();
		view.setViewBlocked(true);
		final CalendarEvent calendarEvent = eventMap.get(event.getId());
		if (dataAccessController.canUpdate(calendarEvent)) {
			calendarEvent.rewriteData(event);
			final UpdateCommand<CalendarEvent> updateCommand = commandFactory.createUpdateCommand(calendarEvent);
			serviceAccess.execute(updateCommand, new ResponseHandler<CalendarEvent>() {

				@Override
				public void handle(final CommandResp<CalendarEvent> response) {
					final UpdateCommandResp<CalendarEvent> updateCommandResp = (UpdateCommandResp<CalendarEvent>) response;
					if (updateCommandResp.getCount() > 0) {
						updateCommandResp.setUpdatedEntity(calendarEvent);
						eventBus.fireEvent(new ServiceResponse<CalendarEvent>(CalendarPresenter.this, updateCommandResp));
					}
					view.setViewBlocked(false);
				}

				@Override
				public void handleException(final Throwable throwable) {
					// TODO Auto-generated method stub
					view.remove(event);
					view.add(calendarEvent.cloneEvent());
					view.setViewBlocked(false);
				}

			});
		} else {
			// restore old view
			tracer(this).debug("Restoring view using source event: " + calendarEvent);
			view.remove(event);
			view.add(calendarEvent.cloneEvent());
			view.setViewBlocked(false);
		}
	}

	/**
	 * Remove event
	 * 
	 * @param termId
	 */
	final public void removeEvent(final int eventId) {
		final CalendarEvent event = eventMap.get(eventId);
		tracer(this).debug("Removing event: " + event);
		validate();
		if (event == null) {
			throw new IllegalArgumentException("Event " + eventId + " isn't displayed on calendar");
		}
		view.setViewBlocked(true);
		if (dataAccessController.canDelete(event)) {
			final DeleteCommand<CalendarEvent> deleteCommand = commandFactory.createDeleteCommand(event);
			serviceAccess.execute(deleteCommand, new ResponseHandler<CalendarEvent>() {

				@Override
				public void handle(final CommandResp<CalendarEvent> response) {
					final DeleteCommandResp<CalendarEvent> deleteCommandResp = (DeleteCommandResp<CalendarEvent>) response;
					if (deleteCommandResp.getCount() > 0) {
						eventBus.fireEvent(new ServiceResponse<CalendarEvent>(CalendarPresenter.this, deleteCommandResp));
					}
					view.setViewBlocked(false);
				}

				@Override
				public void handleException(final Throwable throwable) {
					// TODO Auto-generated method stub
					view.add(event.cloneEvent());
					view.setViewBlocked(false);
				}

			});
		} else {
			// restore old view
			view.add(event.cloneEvent());
			view.setViewBlocked(false);
		}
	}

	/**
	 * Display event which have place between given time
	 * 
	 * @param date
	 * @param events
	 */
	final public <T extends CalendarEvent> void display(final Date startTime, final Date endTime) {
		tracer(this).debug("Displaying events - start time: " + startTime + ", end time: " + endTime);
		validate();
		final SelectCommand<CalendarEvent> selectCommand = commandFactory.createSelectCommnad(startTime, endTime);
		serviceAccess.execute(selectCommand, new ResponseHandler<CalendarEvent>() {

			@Override
			public void handle(final CommandResp<CalendarEvent> response) {
				if (response instanceof SelectCalendarEventCommandResp) {
					final SelectCalendarEventCommandResp selectResp = (SelectCalendarEventCommandResp) response;
					if (!selectResp.getWorkHours().isEmpty()) {
						WorkHoursMap workHoursMap = new WorkHoursMap();
						workHoursMap.addAll(selectResp.getWorkHours());
						dataAccessController.setWorkHoursMap(workHoursMap);
						view.setWorkingHours(workHoursMap);
						// view.clear();
					} else {
						dataAccessController.setWorkHoursMap(null);
					}
					display(selectResp.getData());
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				// ignore
			}

		});
	}

	/**
	 * Display events on view
	 * 
	 * @param events
	 */
	final public <T extends CalendarEvent> void display(final Collection<T> events) {
		eventMap.clear();
		validate();
		for (final T event : events) {
			eventMap.put(event.getId(), event);
			if (!dataAccessController.canDisplay(event)) {
				CalendarEvent filterEvent = dataAccessController.filter(event);
				view.add(filterEvent);
			} else {
				view.add(event);
			}
		}
	}

	final public void setServiceAccess(final ServiceAccess serviceAccess) {
		this.serviceAccess = serviceAccess;
	}

	final public void setCommandFactory(final CalendarCommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	final public CalendarCommandFactory getCommandFactory() {
		return commandFactory;
	}

	/**
	 * Validate state
	 */
	private void validate() {
		if (dataAccessController == null) {
			throw new IllegalArgumentException("Calendar access controller not set");
		} else if (commandFactory == null) {
			throw new IllegalArgumentException("Calendar command factory not set");
		} else if (serviceAccess == null) {
			throw new IllegalArgumentException("Service access is not set");
		} else if (calendarEventDisplayer == null) {
			throw new IllegalArgumentException("Calendar event displayer is not set");
		}
	}

	/**
	 * Get calendar's view
	 * 
	 * @return
	 */
	final public CalendarView getView() {
		return view;
	}

	final public EventBus getEventBus() {
		return eventBus;
	}

	final public CalendarEventDataDisplayer getCalendarEventDisplayer() {
		return calendarEventDisplayer;
	}

	final public void setDataAccessController(UserCalendarAccessController dataAccessController) {
		this.dataAccessController = dataAccessController;
	}

	final public UserCalendarAccessController getDataAccessController() {
		return dataAccessController;
	}

}
