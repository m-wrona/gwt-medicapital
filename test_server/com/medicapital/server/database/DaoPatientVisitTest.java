package com.medicapital.server.database;

import java.util.Date;
import java.util.Set;
import org.junit.Test;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.server.database.hibernate.HibernateDoctor;
import com.medicapital.server.database.hibernate.HibernatePatientVisit;
import com.medicapital.server.database.hibernate.HibernateUser;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.PatientEvaluationEntity;
import com.medicapital.server.database.hibernate.entities.PatientVisitEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoPatientVisitTest extends AbstractDBTestCase {

	private static final DateManager dateManager = DateFactory.createDateManager();
	private HibernatePatientVisit daoPatientVisit;
	private HibernateUser hibernateUser;
	private HibernateDoctor hibernateDoctor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(DoctorEntity.class);
		addAnnotatedClass(SpecializationEntity.class);
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		addAnnotatedClass(PatientVisitEntity.class);
		addAnnotatedClass(PatientEvaluationEntity.class);
		daoPatientVisit = new HibernatePatientVisit();
		daoPatientVisit.setSessionFactory(getHibernateSessionFactory());
		hibernateUser = new HibernateUser();
		hibernateUser.setSessionFactory(getHibernateSessionFactory());
		daoPatientVisit.setDaoUser(hibernateUser);
		hibernateDoctor = new HibernateDoctor();
		hibernateDoctor.setSessionFactory(getHibernateSessionFactory());
		daoPatientVisit.setDaoDoctor(hibernateDoctor);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		hibernateUser.close();
		hibernateDoctor.close();
		daoPatientVisit.close();
	}

	public static PatientVisit createTestVisit() {
		final PatientVisit visit = new PatientVisit();
		visit.setTitle("newVisit");
		visit.setStartTime(new Date());
		dateManager.setDate(visit.getStartTime(), 2012, 3, 14);
		dateManager.setTimeStamp(visit.getStartTime(), 9, 37, 59);
		final Doctor doctor = new Doctor();
		doctor.setId(1);
		visit.setDoctor(doctor);
		final User patient = new User();
		patient.setId(1);
		visit.setPatient(patient);
		final Localization localization = new Localization();
		localization.setId(1);
		visit.setLocalization(localization);

		return visit;
	}

	@Test
	public void testGetVisitById() {
		final PatientVisit visit = daoPatientVisit.get(1);
		assertNotNull(visit);
		assertEquals("visit reason", visit.getTitle());
		assertEquals(2, visit.getPatient().getId());
		assertEquals("patient", visit.getPatient().getLogin());
		assertEquals(1, visit.getDoctor().getId());
		assertEquals("doctor", visit.getDoctor().getUser().getLogin());
		final PatientEvaluation evaluation = visit.getEvaluation();
		assertNotNull("Evaluation not found for visit", evaluation);
		assertTrue(evaluation.getId() > 0);
		assertEquals("evaluation", evaluation.getTitle());
	}

	@Test
	public void testGetVisitByIdNoEvaluation() {
		final PatientVisit visit = daoPatientVisit.get(2);
		assertNotNull(visit);
		assertEquals("visit reason", visit.getTitle());
		assertEquals(2, visit.getPatient().getId());
		assertEquals("patient", visit.getPatient().getLogin());
		assertEquals(1, visit.getDoctor().getId());
		assertEquals("doctor", visit.getDoctor().getUser().getLogin());
		assertNull("Evaluation set but should be empty", visit.getEvaluation());
	}

	@Test
	public void testGetVisitsByLogin() {
		final Set<PatientVisit> visits = daoPatientVisit.getPatientVisits("patient", 0, 10);
		assertNotNull(visits);
		assertEquals(4, visits.size());
	}

	@Test
	public void testGetDoctorVisitsByDate() {
		final Date beginDate = new Date();
		dateManager.setDate(beginDate, 2000, 1, 1);
		final Date endDate = new Date();
		dateManager.setDate(endDate, 2030, 1, 1);
		final Set<PatientVisit> visits = daoPatientVisit.getDoctorVisits(1, beginDate, endDate);
		assertNotNull(visits);
		assertEquals(4, visits.size());
		final PatientVisit visit = visits.iterator().next();
		assertEquals("visit reason", visit.getTitle());
		assertEquals(2, visit.getPatient().getId());
		assertEquals("patient", visit.getPatient().getLogin());
		assertEquals(1, visit.getDoctor().getId());
		assertEquals("doctor", visit.getDoctor().getUser().getLogin());
	}

	@Test
	public void testGetDoctorVisits() {
		Set<PatientVisit> visits = daoPatientVisit.getDoctorVisits(1, 0, 10);
		assertNotNull(visits);
		assertEquals(4, visits.size());
	}

	@Test
	public void testGetDoctorVisitsCount() {
		assertEquals(4, daoPatientVisit.getDoctorPatientVisitsCount(1));
		assertEquals(0, daoPatientVisit.getDoctorPatientVisitsCount(2));
	}

	@Test
	public void testGetPatientVisitsCount() {
		assertEquals(4, daoPatientVisit.getPatientVisitsCount("patient"));
		assertEquals(0, daoPatientVisit.getPatientVisitsCount("notExistingLogin"));
	}

	@Test
	public void testDeleteVisit() {
		daoPatientVisit.delete(1);
		assertNull(daoPatientVisit.get(1));
	}

	@Test
	public void testCreateVisit() {
		final PatientVisit newVisit = createTestVisit();

		final int visitId = daoPatientVisit.create(newVisit);
		assertTrue("Created visit's id unknown: " + visitId, visitId > 0);
		final PatientVisit createdVisit = daoPatientVisit.get(visitId);
		assertNotNull(createdVisit);
		assertEquals("newVisit", createdVisit.getTitle());
		assertEquals("Wed Mar 14 09:37:59 CET 2012", createdVisit.getStartTime().toString());
		assertEquals(1, createdVisit.getPatient().getId());
		assertEquals(1, createdVisit.getDoctor().getId());
		assertEquals(1, createdVisit.getLocalization().getId());
	}

	@Test
	public void testUpdateVisit() {
		final PatientVisit visit = createTestVisit();

		final int visitId = daoPatientVisit.create(visit);
		visit.setId(visitId);
		visit.setTitle("newVisit updated");
		daoPatientVisit.update(visit);

		assertTrue("Created visit's id unknown: " + visitId, visitId > 0);
		final PatientVisit createdVisit = daoPatientVisit.get(visitId);
		assertNotNull(createdVisit);
		assertEquals("newVisit updated", createdVisit.getTitle());
		assertEquals("Wed Mar 14 09:37:59 CET 2012", createdVisit.getStartTime().toString());
		assertEquals(1, createdVisit.getPatient().getId());
		assertEquals(1, createdVisit.getDoctor().getId());
		assertEquals(1, createdVisit.getLocalization().getId());
	}

	@Test
	public void testIsVisitTermFree() {
		Date startTime = new Date();
		// check term in full visit time
		dateManager.setDate(startTime, 2010, 1, 2);
		dateManager.setTimeStamp(startTime, 16, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2010, 1, 2);
		dateManager.setTimeStamp(endTime, 16, 15, 0);
		assertFalse(daoPatientVisit.isVisitTermFree(1, startTime, endTime));

		// check term that takes only part of visit
		dateManager.setDate(startTime, 2010, 1, 2);
		dateManager.setTimeStamp(startTime, 16, 5, 0);
		dateManager.setDate(endTime, 2010, 1, 2);
		dateManager.setTimeStamp(endTime, 16, 10, 0);
		assertFalse(daoPatientVisit.isVisitTermFree(1, startTime, endTime));

		dateManager.setDate(startTime, 2010, 1, 2);
		dateManager.setTimeStamp(startTime, 15, 55, 0);
		dateManager.setDate(endTime, 2010, 1, 2);
		dateManager.setTimeStamp(endTime, 16, 10, 0);
		assertFalse(daoPatientVisit.isVisitTermFree(1, startTime, endTime));

		// term wider then visit scope
		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 9, 55, 0);
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 40, 0);
		assertFalse(daoPatientVisit.isVisitTermFree(1, startTime, endTime));

		// check free term
		dateManager.setDate(startTime, 2010, 1, 2);
		dateManager.setTimeStamp(startTime, 16, 15, 0);
		dateManager.setDate(endTime, 2010, 1, 2);
		dateManager.setTimeStamp(endTime, 16, 30, 0);
		assertTrue(daoPatientVisit.isVisitTermFree(1, startTime, endTime));

		dateManager.setDate(startTime, 2010, 1, 2);
		dateManager.setTimeStamp(startTime, 15, 45, 0);
		dateManager.setDate(endTime, 2010, 1, 2);
		dateManager.setTimeStamp(endTime, 16, 0, 0);
		assertTrue(daoPatientVisit.isVisitTermFree(1, startTime, endTime));
	}

	@Test
	public void testIsVisitTermFreeCurrentDate() {
		assertTrue(daoPatientVisit.isVisitTermFree(1, new Date(), new Date()));
	}

	@Test
	public void testIsVisitTermEditable() {
		Date startTime = new Date();
		// the same term
		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 10, 0, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 15, 0);
		assertTrue(daoPatientVisit.isVisitTermExpendable(1, startTime, endTime));

		// move visit a little bit earlier
		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 9, 50, 0);
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 10, 0);
		assertTrue(daoPatientVisit.isVisitTermExpendable(1, startTime, endTime));

		// visit is colliding with other terms
		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 10, 0, 0);
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 30, 0);
		assertFalse(daoPatientVisit.isVisitTermExpendable(1, startTime, endTime));

		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 9, 55, 0);
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 20, 0);
		assertFalse(daoPatientVisit.isVisitTermExpendable(1, startTime, endTime));

		dateManager.setDate(startTime, 2012, 1, 20);
		dateManager.setTimeStamp(startTime, 9, 55, 0);
		dateManager.setDate(endTime, 2012, 1, 20);
		dateManager.setTimeStamp(endTime, 10, 40, 0);
		assertFalse(daoPatientVisit.isVisitTermExpendable(1, startTime, endTime));

	}

	@Test
	public void testLockVisit() {
		Date startTime = new Date();
		dateManager.setDate(startTime, 2011, 4, 1);
		dateManager.setTimeStamp(startTime, 12, 30, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 4, 1);
		dateManager.setTimeStamp(endTime, 12, 45, 0);
		Date bookingTime = new Date();
		dateManager.setDate(bookingTime, 2011, 4, 1);
		dateManager.setTimeStamp(bookingTime, 12, 30, 0);

		int visitId = daoPatientVisit.lockVisit(2, 1, startTime, endTime, bookingTime, "test book visit");
		PatientVisit bookVisit = daoPatientVisit.get(visitId);
		assertNotNull(bookVisit);
		assertTrue(bookVisit.getId() > 0);
		assertEquals("Fri Apr 01 12:30:00 CEST 2011", bookVisit.getLockTime().toString());
		assertEquals(1, bookVisit.getDoctor().getId());
		assertEquals("doctor", bookVisit.getDoctor().getUser().getLogin());
		assertEquals(2, bookVisit.getPatient().getId());
		assertEquals("patient", bookVisit.getPatient().getLogin());
	}

	@Test
	public void testUpdateLockedVisit() {
		Date startTime = new Date();
		dateManager.setDate(startTime, 2011, 4, 1);
		dateManager.setTimeStamp(startTime, 12, 30, 0);
		Date endTime = new Date();
		dateManager.setDate(endTime, 2011, 4, 1);
		dateManager.setTimeStamp(endTime, 12, 45, 0);
		Date bookingTime = new Date();
		dateManager.setDate(bookingTime, 2011, 4, 1);
		dateManager.setTimeStamp(bookingTime, 12, 30, 0);

		int visitId = daoPatientVisit.lockVisit(2, 1, startTime, endTime, bookingTime, "test update booked visit");
		PatientVisit bookVisit = daoPatientVisit.get(visitId);
		assertNotNull(bookVisit);
		assertTrue(bookVisit.getId() > 0);
		assertEquals("Fri Apr 01 12:30:00 CEST 2011", bookVisit.getLockTime().toString());
		assertEquals(1, bookVisit.getDoctor().getId());
		assertEquals("doctor", bookVisit.getDoctor().getUser().getLogin());
		assertEquals(2, bookVisit.getPatient().getId());
		assertEquals("patient", bookVisit.getPatient().getLogin());

		dateManager.setDate(startTime, 2011, 4, 1);
		dateManager.setTimeStamp(startTime, 15, 30, 0);
		dateManager.setDate(endTime, 2011, 4, 1);
		dateManager.setTimeStamp(endTime, 15, 45, 0);
		dateManager.setDate(bookingTime, 2011, 4, 1);
		dateManager.setTimeStamp(bookingTime, 15, 30, 0);

		daoPatientVisit.updateLockedVisit(bookVisit.getId(), startTime, endTime, bookingTime, "test update booked visit");
		bookVisit = daoPatientVisit.get(visitId);
		assertNotNull(bookVisit);
		assertTrue(bookVisit.getId() > 0);
		assertEquals("Fri Apr 01 15:30:00 CEST 2011", bookVisit.getLockTime().toString());
		assertEquals(1, bookVisit.getDoctor().getId());
		assertEquals("doctor", bookVisit.getDoctor().getUser().getLogin());
		assertEquals(2, bookVisit.getPatient().getId());
		assertEquals("patient", bookVisit.getPatient().getLogin());
	}

}
