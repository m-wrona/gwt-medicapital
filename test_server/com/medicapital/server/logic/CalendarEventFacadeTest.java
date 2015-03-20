package com.medicapital.server.logic;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.entities.PatientVisit;

public class CalendarEventFacadeTest {

	private PatientVisitFacade patientVisitFacade;
	private CalendarEventFacade calendarEventFacade;

	@Before
	public void init() {
		patientVisitFacade = mock(PatientVisitFacade.class);
		calendarEventFacade = new CalendarEventFacade();
		calendarEventFacade.setPatientVisitFacade(patientVisitFacade);
	}

	@Test
	public void testCreatePatientVisit() {
		calendarEventFacade.create(new PatientVisit());
		verify(patientVisitFacade).create(any(PatientVisit.class));
	}

	@Test
	public void testCreateCalendarEvent() {
		fail("Implement this");
	}

	@Test
	public void testUpdatePatientVisit() {
		calendarEventFacade.update(new PatientVisit());
		verify(patientVisitFacade).update(any(PatientVisit.class));
	}

	@Test
	public void testUpdateCalendarEvent() {
		fail("Implement this");
	}
	
	@Test
	public void testDeletePatientVisit() {
		calendarEventFacade.deleteEvent(1, PatientVisit.class);
		verify(patientVisitFacade).delete(1);
	}

	@Test
	public void testDeleteCalendarEvent() {
		fail("Implement this");
	}

}
