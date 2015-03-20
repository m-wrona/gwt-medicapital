package com.medicapital.client.calendar;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.medicapital.client.calendar.access.UserCalendarAccessController;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.calendar.SelectCalendarEventCommandResp;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CalendarPresenter.class })
public class CalendarPresenterTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testDisplayEvent() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(dataAccessController.canDisplay(any(CalendarEvent.class))).thenReturn(true);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		calendarPresenter.setCommandFactory(new CalendarCommandFactory());
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		events.add(event1);

		calendarPresenter.display(events);
		verify(view).add(any(PatientVisit.class));
	}

	@Test
	public void testDisplayEventByDate() {
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);

		SelectCalendarEventCommandResp selectResp = new SelectCalendarEventCommandResp();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		selectResp.add(event1);
		serviceAccess.addResponse(selectResp);

		when(dataAccessController.canDisplay(any(CalendarEvent.class))).thenReturn(true);
		calendarPresenter.display(startTime, endTime);
		verify(commandFactory).createSelectCommnad(startTime, endTime);
		assertEquals(1, serviceAccess.getReceived().size());
		verify(view).add(any(CalendarEvent.class));
	}

	@Test
	public void testAddEventClick() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		calendarPresenter.setCommandFactory(new CalendarCommandFactory());
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);
		when(dataAccessController.canAdd(startTime, endTime)).thenReturn(true);
		calendarPresenter.addEventClick(startTime, endTime);
		verify(calendarEventDisplayer).displayNewEventView(startTime, endTime);
	}

	@Test
	public void testEditEventClick() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		calendarPresenter.setCommandFactory(new CalendarCommandFactory());
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		events.add(event1);
		calendarPresenter.display(events);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);
		when(dataAccessController.canUpdate(event1)).thenReturn(true);
		calendarPresenter.editEventClick(event1.getId());

		verify(calendarEventDisplayer).displayEditEventView(event1);
	}

	@Test
	public void testEditEventClickError() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		calendarPresenter.setCommandFactory(new CalendarCommandFactory());
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);
		try {
			calendarPresenter.editEventClick(1);
			fail("Event isn't displayed in calendar");
		} catch (IllegalArgumentException e) {
			assertEquals("Event '1' is not displayed on current calendar", e.getMessage());
		}
	}

	@Test
	public void testAddEvent() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canAdd(any(CalendarEvent.class))).thenReturn(true);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2012, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2012, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);
		CreateCommandResp<CalendarEvent> createCommandResp = spy(new CreateCommandResp<CalendarEvent>(CalendarEvent.class, event1.getId()));
		serviceAccess.addResponse(createCommandResp);

		calendarPresenter.addEvent(event1);
		verify(view).setViewBlocked(true);
		verify(commandFactory).createCreateCommand(event1);
		assertEquals(1, serviceAccess.getReceived().size());
		verify(createCommandResp).setCreatedEntity(event1);
		verify(event1).cloneEvent();
		verify(view).add(any(CalendarEvent.class));
		verify(view).setViewBlocked(false);
	}

	@Test
	public void testAddEventFallback() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canAdd(any(CalendarEvent.class))).thenReturn(false);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2010, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 15, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);

		calendarPresenter.addEvent(event1);
		verify(view).remove(event1);
	}

	@Test
	public void testEditEvent() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canUpdate(any(CalendarEvent.class))).thenReturn(true);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2012, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2012, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);
		UpdateCommandResp<CalendarEvent> updateCommandResp = spy(new UpdateCommandResp<CalendarEvent>(CalendarEvent.class, event1.getId()));
		serviceAccess.addResponse(updateCommandResp);
		
		calendarPresenter.display(events);
		calendarPresenter.updateEvent(event1);
		verify(view).setViewBlocked(true);
		verify(commandFactory).createUpdateCommand(event1);
		assertEquals(1, serviceAccess.getReceived().size());
		verify(updateCommandResp).setUpdatedEntity(event1);
		verify(event1).rewriteData(event1);
		verify(view).setViewBlocked(false);
	}

	@Test
	public void testUpdateEventFallback() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canUpdate(any(CalendarEvent.class))).thenReturn(false);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2010, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 15, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);
		calendarPresenter.display(events);
		calendarPresenter.updateEvent(event1);
		verify(event1).cloneEvent();
		verify(view).remove(event1);
	}

	@Test
	public void testRemoveEvent() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canDelete(any(CalendarEvent.class))).thenReturn(true);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2012, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2012, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 0, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);
		calendarPresenter.display(events);

		DeleteCommandResp<CalendarEvent> deleteCommandResp = spy(new DeleteCommandResp<CalendarEvent>(CalendarEvent.class, event1.getId()));
		serviceAccess.addResponse(deleteCommandResp);

		calendarPresenter.removeEvent(event1.getId());
		verify(view).setViewBlocked(true);
		verify(commandFactory).createDeleteCommand(event1);
		assertEquals(1, serviceAccess.getReceived().size());
		verify(view).setViewBlocked(false);
	}

	@Test
	public void testRemoveEventFallback() {
		CalendarView view = mock(CalendarView.class);
		TestEventBus eventBus = new TestEventBus();
		CalendarEventDataDisplayer calendarEventDisplayer = mock(CalendarEventDataDisplayer.class);
		UserCalendarAccessController dataAccessController = mock(UserCalendarAccessController.class);
		when(calendarEventDisplayer.getDataAccessController()).thenReturn(dataAccessController);
		when(dataAccessController.canDelete(any(CalendarEvent.class))).thenReturn(false);
		CalendarPresenter calendarPresenter = new CalendarPresenter(view, eventBus, calendarEventDisplayer);
		calendarPresenter.setDataAccessController(dataAccessController);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		calendarPresenter.setServiceAccess(serviceAccess);
		CalendarCommandFactory commandFactory = spy(new CalendarCommandFactory());
		calendarPresenter.setCommandFactory(commandFactory);

		Date startTime = new Date();
		dateManager.setDate(startTime, 2010, 1, 1);
		dateManager.setTimeStamp(startTime, 8, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2010, 1, 1);
		dateManager.setTimeStamp(endTime, 8, 15, 0);

		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		CalendarEvent event1 = spy(new CalendarEvent());
		event1.setId(1);
		event1.setTitle("event1");
		event1.setStartTime(startTime);
		event1.setEndTime(endTime);
		events.add(event1);
		calendarPresenter.display(events);
		calendarPresenter.removeEvent(event1.getId());
		verify(view, times(2)).add(any(CalendarEvent.class));
	}
}
