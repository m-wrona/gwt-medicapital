package com.medicapital.client.calendar.access;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.common.lang.GenericText;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Lang.class })
public class UserCalendarAccessControllerTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testCanDisplayEvent_UserOwner() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertTrue(userCalendarAccessController.canDisplay(new CalendarEvent()));
	}

	@Test
	public void testCanDisplayEvent_UnknownVisitor() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(2);
		assertFalse(userCalendarAccessController.canDisplay(new CalendarEvent()));
	}

	@Test
	public void testCanAddEvent_UserOwner() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertTrue(userCalendarAccessController.canAdd(new Date(), new Date()));
	}

	@Test
	public void testCanAddEvent_UnknownVisitor() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(2);
		assertFalse(userCalendarAccessController.canAdd(new Date(), new Date()));
	}

	@Test
	public void testCanAddEvent_PatientVisit() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertFalse(userCalendarAccessController.canAdd(new PatientVisit()));
	}

	@Test
	public void testCanAddCalendarEvent_UserOwner() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertTrue(userCalendarAccessController.canAdd(new CalendarEvent()));
	}

	@Test
	public void testCanAddCalendarEvent_UnknownVisitor() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(2);
		assertFalse(userCalendarAccessController.canAdd(new CalendarEvent()));
	}

	@Test
	public void testCanUpdateEvent_PatientVisit() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertFalse(userCalendarAccessController.canUpdate(new PatientVisit()));
	}

	@Test
	public void testCanUpdateCalendarEvent_UserOwner() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		CalendarEvent calendarEvent = new CalendarEvent();
		Date date = new Date();
		dateManager.setDate(date, 2030, 1, 1);
		calendarEvent.setStartTime(date);
		calendarEvent.setEndTime(date);
		assertTrue(userCalendarAccessController.canUpdate(calendarEvent));
	}

	@Test
	public void testCanUpdateCalendarEvent_PastEvent() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		CalendarEvent calendarEvent = new CalendarEvent();
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 1);
		calendarEvent.setStartTime(date);
		calendarEvent.setEndTime(date);
		assertFalse(userCalendarAccessController.canUpdate(calendarEvent));
	}

	@Test
	public void testCanUpdateCalendarEvent_UnknownVisitor() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(2);
		assertFalse(userCalendarAccessController.canUpdate(new CalendarEvent()));
	}

	@Test
	public void testCanDeleteEvent_PatientVisit() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		assertFalse(userCalendarAccessController.canDelete(new PatientVisit()));
	}

	@Test
	public void testCanDeleteCalendarEvent_UserOwner() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		CalendarEvent calendarEvent = new CalendarEvent();
		Date date = new Date();
		dateManager.setDate(date, 2030, 1, 1);
		calendarEvent.setStartTime(date);
		calendarEvent.setEndTime(date);
		assertTrue(userCalendarAccessController.canDelete(calendarEvent));
	}

	@Test
	public void testCanDeleteCalendarEvent_PastEvent() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(1);
		CalendarEvent calendarEvent = new CalendarEvent();
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 1);
		calendarEvent.setStartTime(date);
		calendarEvent.setEndTime(date);
		assertFalse(userCalendarAccessController.canDelete(calendarEvent));
	}

	@Test
	public void testCanDeleteCalendarEvent_UnknownVisitor() {
		UserCalendarAccessController userCalendarAccessController = new UserCalendarAccessController();
		userCalendarAccessController.setOwnerId(1);
		userCalendarAccessController.setVisitorId(2);
		assertFalse(userCalendarAccessController.canDelete(new CalendarEvent()));
	}

	@Test
	public void testCanEvaluationBeDisplayed_UnknownVisitor() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		visit.setEvaluation(new PatientEvaluation());
		accessController.setOwnerId(patient.getId());
		assertFalse(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_PatientOwner() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		visit.setEvaluation(new PatientEvaluation());
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		assertTrue(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_NotPatientVisit() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		visit.setEvaluation(new PatientEvaluation());
		accessController.setOwnerId(3);
		accessController.setVisitorId(3);
		assertFalse(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_NoEvaluation() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		assertFalse(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_UnknownVisitor() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2000, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2000, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(3);
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_PatientOwner() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2000, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2000, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		assertTrue(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_EvaluationAdded() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2000, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2000, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		visit.setEvaluation(new PatientEvaluation());
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_FutureVisit() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanTimeTablesBeChanged_PatientVisit() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		accessController.setOwnerId(1);
		accessController.setVisitorId(1);
		assertFalse(accessController.canTimeTablesBeChanged(new PatientVisit()));
	}

	@Test
	public void testCanTimeTablesBeChanged_UnknownVisitor() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		accessController.setOwnerId(1);
		accessController.setVisitorId(2);
		assertFalse(accessController.canTimeTablesBeChanged());
	}

	@Test
	public void testCanTimeTablesBeChanged_PatientOwner() {
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		accessController.setOwnerId(1);
		accessController.setVisitorId(1);
		assertTrue(accessController.canTimeTablesBeChanged());
	}

	@Test
	public void testFilterPatientVisit() {
		mockStatic(UIUtil.class);
		mockStatic(Lang.class);
		GenericText genericText = mock(GenericText.class);
		when(Lang.generic()).thenReturn(genericText);
		UserCalendarAccessController accessController = new UserCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		visit.setTitle("visit title");
		visit.setDescription("description");
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		visit.setEvaluation(new PatientEvaluation());
		visit.setLocalization(new Localization());
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		visit.setStartTime(dateFrom);
		visit.setEndTime(dateTo);
		accessController.setOwnerId(patient.getId());
		accessController.setVisitorId(patient.getId());
		PatientVisit filteredVisit = accessController.filter(visit);
		verify(genericText).patientVisit();
		assertEquals(-1, filteredVisit.getId());
		assertNull(filteredVisit.getDescription());
		assertNull(filteredVisit.getDoctor());
		assertNull(filteredVisit.getPatient());
		assertNull(filteredVisit.getEvaluation());
		assertNull(filteredVisit.getLocalization());
	}
}
