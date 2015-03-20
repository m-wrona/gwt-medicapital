package com.medicapital.server.logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.server.database.DaoPatientVisit;

public class PatientVisitFacadeTest {

	private DaoPatientVisit daoPatientVisit;
	private PatientVisitFacade patientVisitFacade;

	@Before
	public void init() {
		daoPatientVisit = mock(DaoPatientVisit.class);
		patientVisitFacade = new PatientVisitFacade();
		patientVisitFacade.setDaoPatientVisit(daoPatientVisit);
	}

	@Test
	public void testGetDoctorPatientVisitsCount() {
		patientVisitFacade.getDoctorPatientVisitsCount(1);
		verify(daoPatientVisit).getDoctorPatientVisitsCount(1);
	}

	@Test
	public void testPatientVisitsCount() {
		patientVisitFacade.getPatientVisitsCount("testLogin");
		verify(daoPatientVisit).getPatientVisitsCount("testLogin");
	}

	@Test
	public void testGetPatientVisits() {
		patientVisitFacade.getPatientVisits("testLogin", 0, 10);
		verify(daoPatientVisit).getPatientVisits("testLogin", 0, 10);
	}

	@Test
	public void testGetDoctorVisitsByDate() {
		Date date = new Date();
		patientVisitFacade.getDoctorVisits(1, date, date);
		verify(daoPatientVisit).getDoctorVisits(1, date, date);
	}

	@Test
	public void testGetDoctorVisits() {
		patientVisitFacade.getDoctorVisits(1, 1, 1);
		verify(daoPatientVisit).getDoctorVisits(1, 1, 1);
	}

	@Test
	public void testLockVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2030, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		when(daoPatientVisit.isVisitTermFree(anyInt(), any(Date.class), any(Date.class))).thenReturn(true);
		when(daoPatientVisit.lockVisit(anyInt(), anyInt(), any(Date.class), any(Date.class), any(Date.class), anyString())).thenReturn(5);
		when(daoPatientVisit.get(5)).thenReturn(visit);
		PatientVisit updatedVisit = patientVisitFacade.lockVisit(-1, 1, 1, new Date(), new Date(), "test title");
		verify(daoPatientVisit).lockVisit(anyInt(), anyInt(), any(Date.class), any(Date.class), any(Date.class), anyString());
		assertNotNull(updatedVisit);
	}

	@Test
	public void testUpdateLockedVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2030, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		when(daoPatientVisit.isVisitTermExpendable(anyInt(), any(Date.class), any(Date.class))).thenReturn(true);
		when(daoPatientVisit.get(anyInt())).thenReturn(visit);
		PatientVisit updatedVisit = patientVisitFacade.lockVisit(1, 1, 1, new Date(), new Date(), "test title");
		verify(daoPatientVisit).updateLockedVisit(anyInt(), any(Date.class), any(Date.class), any(Date.class), anyString());
		assertNotNull(updatedVisit);
	}

	@Test(expected = ServerException.class)
	public void testUpdateLockedVisitError() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2030, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		when(daoPatientVisit.isVisitTermExpendable(anyInt(), any(Date.class), any(Date.class))).thenReturn(false);
		patientVisitFacade.lockVisit(1, 1, 1, new Date(), new Date(), "test title");
	}

	@Test
	public void testDeleteVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2030, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		patientVisitFacade.delete(visit.getId());
		verify(daoPatientVisit).delete(visit.getId());
	}

	@Test(expected = ServerException.class)
	public void testDeleteNotEditableVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2000, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		patientVisitFacade.delete(visit.getId());
	}

	@Test
	public void testEditVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2030, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		patientVisitFacade.update(visit);
		verify(daoPatientVisit).update(visit);
	}

	@Test(expected = ServerException.class)
	public void testUpdateNotEditableVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2000, 1, 1);
		when(patientVisitFacade.get(visit.getId())).thenReturn(visit);
		patientVisitFacade.update(visit);
	}
}
