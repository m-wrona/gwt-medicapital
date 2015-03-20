package com.medicapital.client.calendar.access;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.mockito.Mockito.*;
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
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.collections.WorkHoursMap;
import com.medicapital.common.lang.WarningText;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DoctorCalendarAccessController.class, UIUtil.class, WorkHoursMap.class, Lang.class })
public class DoctorCalendarAccessControllerTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testCanDisplayEvent_NoEventId() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		CalendarEvent event = new CalendarEvent();
		event.setId(-1);
		assertFalse(accessController.canDisplay(event));
	}

	@Test
	public void testCanDisplayEvent_PatientVisitPatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		accessController.setVisitorId(patient.getId());
		accessController.setOwnerId(doctor.getId());
		assertTrue(accessController.canDisplay(visit));
	}

	@Test
	public void testCanDisplayEvent_PatientVisitDoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		assertTrue(accessController.canDisplay(visit));
	}

	@Test
	public void testCanDisplayEvent_PatientVisitUnknownVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		PatientVisit visit = new PatientVisit();
		User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		Doctor doctor = new Doctor();
		User doctorUser = new User();
		doctorUser.setId(2);
		doctor.setUser(doctorUser);
		visit.setDoctor(doctor);
		assertFalse(accessController.canDisplay(visit));
	}

	@Test
	public void testCanDisplayEvent_DoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		CalendarEvent event = new CalendarEvent();
		accessController.setVisitorId(1);
		accessController.setOwnerId(1);
		assertTrue(accessController.canDisplay(event));
	}

	@Test
	public void testCanDisplayEvent_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		CalendarEvent event = new CalendarEvent();
		accessController.setVisitorId(2);
		accessController.setOwnerId(1);
		assertFalse(accessController.canDisplay(event));
	}

	@Test
	public void testCanAddEvent_DoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		assertTrue(accessController.canAdd(dateFrom, dateTo));
	}

	@Test
	public void testCanAddEvent_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(1);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		assertTrue(accessController.canAdd(dateFrom, dateTo));
	}

	@Test
	public void testCanAddEvent_WorkHoursTrue() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		WorkHoursMap workHoursMap = mock(WorkHoursMap.class);
		accessController.setWorkHoursMap(workHoursMap);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		when(workHoursMap.isWorking(dateFrom, dateTo)).thenReturn(true);
		assertTrue(accessController.canAdd(dateFrom, dateTo));
		verifyNoMoreInteractions(workHoursMap);
	}

	@Test
	public void testCanAddEvent_WorkHoursFalse() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		WorkHoursMap workHoursMap = mock(WorkHoursMap.class);
		accessController.setWorkHoursMap(workHoursMap);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		when(workHoursMap.isWorking(dateFrom, dateTo)).thenReturn(false);
		assertTrue(accessController.canAdd(dateFrom, dateTo));
		verifyNoMoreInteractions(workHoursMap);
	}

	@Test
	public void testCanAddEvent_PastTime() {
		mockStatic(UIUtil.class);
		mockStatic(Lang.class);
		WarningText warningText = mock(WarningText.class);
		when(Lang.warnings()).thenReturn(warningText);
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2000, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2000, 1, 1);
		assertFalse(accessController.canAdd(dateFrom, dateTo));
		verify(warningText).eventInPastCantBeAdded();
		verifyStatic();
	}

	@Test
	public void testCanModifyEvent_NoId() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		CalendarEvent event = new CalendarEvent();
		event.setId(-1);
		accessController.setVisitorId(2);
		accessController.setOwnerId(1);
		assertFalse(accessController.canDelete(event));
	}

	@Test
	public void testCanModifyEvent_FutureTime() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		CalendarEvent event = new CalendarEvent();
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		event.setStartTime(dateFrom);
		event.setEndTime(dateTo);
		assertTrue(accessController.canUpdate(event));
	}

	@Test
	public void testCanModifyEvent_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(1);
		CalendarEvent event = new CalendarEvent();
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2030, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2030, 1, 1);
		event.setStartTime(dateFrom);
		event.setEndTime(dateTo);
		assertFalse(accessController.canUpdate(event));
	}

	@Test
	public void testCanModifyEvent_PastTime() {
		mockStatic(UIUtil.class);
		mockStatic(Lang.class);
		WarningText warningText = mock(WarningText.class);
		when(Lang.warnings()).thenReturn(warningText);
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		accessController.setVisitorId(2);
		accessController.setOwnerId(2);
		CalendarEvent event = new CalendarEvent();
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2000, 1, 1);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2000, 1, 1);
		event.setStartTime(dateFrom);
		event.setEndTime(dateTo);
		assertFalse(accessController.canDelete(event));
		verify(warningText).eventInPastCantBeAdded();
		verifyStatic();
	}

	@Test
	public void testCanModifyEvent_PatientVisitDoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		assertTrue(accessController.canAdd(visit));
	}

	@Test
	public void testCanModifyEvent_PatientVisitPatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setVisitorId(patient.getId());
		accessController.setOwnerId(doctor.getId());
		assertTrue(accessController.canAdd(visit));
	}

	@Test
	public void testCanModifyEvent_PatientVisitUnknownVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		assertFalse(accessController.canAdd(visit));
	}

	@Test
	public void testCanModifyEvent_WorkHoursTrue() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		WorkHoursMap workHoursMap = mock(WorkHoursMap.class);
		accessController.setWorkHoursMap(workHoursMap);
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
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		when(workHoursMap.isWorking(visit)).thenReturn(true);
		assertTrue(accessController.canAdd(visit));
		verifyNoMoreInteractions(workHoursMap);
	}

	@Test
	public void testCanModifyEvent_WorkHoursFalse() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
		WorkHoursMap workHoursMap = mock(WorkHoursMap.class);
		accessController.setWorkHoursMap(workHoursMap);
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
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		when(workHoursMap.isWorking(visit)).thenReturn(false);
		assertTrue(accessController.canAdd(visit));
		verifyNoMoreInteractions(workHoursMap);
	}

	@Test
	public void testCanEvaluationBeDisplayed_DoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(doctor.getId());
		assertTrue(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(patient.getId());
		assertTrue(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_UnknownVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		assertFalse(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeDisplayed_NoEvaluation() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(doctor.getId());
		assertFalse(accessController.canEvaluationBeDisplayed(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_DoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(doctor.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(patient.getId());
		assertTrue(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_UnknownVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_PatientVisitorEvaluaitonAdded() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(patient.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanEvaluationBeCreated_PatientVisitorFutureVisit() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setOwnerId(doctor.getId());
		accessController.setVisitorId(patient.getId());
		assertFalse(accessController.canEvaluationBeCreated(visit));
	}

	@Test
	public void testCanChangePatientVisitTime_PatientVisitor() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setVisitorId(patient.getId());
		accessController.setOwnerId(doctor.getId());
		assertFalse(accessController.canTimeTablesBeChanged(visit));
	}

	@Test
	public void testCanChangePatientVisitTime_DoctorOwner() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		assertTrue(accessController.canTimeTablesBeChanged(visit));
	}

	@Test
	public void testCanChangePatientVisitTime_PastVisit() {
		DoctorCalendarAccessController accessController = new DoctorCalendarAccessController();
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
		accessController.setVisitorId(doctor.getId());
		accessController.setOwnerId(doctor.getId());
		assertFalse(accessController.canTimeTablesBeChanged(visit));
	}

}
