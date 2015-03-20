package com.medicapital.server.database;

import java.util.List;
import org.junit.Test;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.server.database.hibernate.HibernateDoctor;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoDoctorTest extends AbstractDBTestCase {

	private HibernateDoctor daoDoctor;

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
		daoDoctor = new HibernateDoctor();
		daoDoctor.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoDoctor.close();
		daoDoctor = null;
	}

	private Doctor createTestDoctor() {
		final Doctor doctor = new Doctor();
		doctor.setWorkId("testWorkId");
		doctor.setNotes("test doctor");
		doctor.setAverageEvaluation(3);
		doctor.setUser(DaoUserTest.createTestUser());
		return doctor;
	}

	@Test
	public void testGetDoctorCount() {
		assertEquals(4, daoDoctor.getCount());
	}

	@Test
	public void testGetDoctorById() {
		final Doctor doctor = daoDoctor.get(1);
		assertNotNull(doctor);
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertNotNull(doctor.getUser().getLocalization());
		assertEquals(1, doctor.getUser().getLocalization().getCityId());
	}

	@Test
	public void testGetDoctorByLogin() {
		final Doctor doctor = daoDoctor.get("testDoctor");
		assertNotNull(doctor);
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());
		assertNotNull(doctor.getUser().getLocalization());
		assertEquals(1, doctor.getUser().getLocalization().getCityId());
	}

	@Test
	public void testFindDoctors() {
		final SearchDoctorCriteria searchCriteria = new SearchDoctorCriteria();
		final Localization localization = new Localization();
		searchCriteria.setLocalization(localization);
		localization.setCityId(1);
		final List<Doctor> doctors = daoDoctor.searchDoctors(searchCriteria);
		assertNotNull(doctors);
		assertEquals(1, doctors.size());
		final Doctor doctor = doctors.iterator().next();
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());
	}

	@Test
	public void testFindDoctors_ByFullName() {
		final SearchDoctorCriteria searchCriteria = new SearchDoctorCriteria();
		searchCriteria.setFirstName("firstName");
		searchCriteria.setLastName("lastName");
		final List<Doctor> doctors = daoDoctor.searchDoctors(searchCriteria);
		assertNotNull(doctors);
		assertEquals(1, doctors.size());
		final Doctor doctor = doctors.iterator().next();
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());
	}

	@Test
	public void testFindDoctors_ByEvaluation() {
		final SearchDoctorCriteria searchCriteria = new SearchDoctorCriteria();
		searchCriteria.setEvaluationFrom(1);
		searchCriteria.setEvaluationTo(4);
		List<Doctor> doctors = daoDoctor.searchDoctors(searchCriteria);
		assertNotNull(doctors);
		assertEquals(4, doctors.size());
		Doctor doctor = doctors.iterator().next();
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());

		searchCriteria.setEvaluationFrom(1);
		searchCriteria.setEvaluationTo(1.9f);
		doctors = daoDoctor.searchDoctors(searchCriteria);
		assertNotNull(doctors);
		assertEquals(1, doctors.size());
		doctor = doctors.iterator().next();
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());
		
		searchCriteria.setEvaluationFrom(0);
		searchCriteria.setEvaluationTo(1);
		doctors = daoDoctor.searchDoctors(searchCriteria);
		assertNotNull(doctors);
		assertEquals(1, doctors.size());
		doctor = doctors.iterator().next();
		assertEquals(1, doctor.getId());
		assertNotNull(doctor.getUser());
		assertEquals(1, doctor.getUser().getId());
		assertEquals("testDoctor", doctor.getUser().getLogin());
	}

	@Test
	public void testGetDoctorProfilePhoto() {
		assertNull(daoDoctor.getProfilePhoto(1));
	}

	@Test
	public void testCreateDoctor() {
		final Doctor newDoctor = createTestDoctor();
		final int doctorId = daoDoctor.create(newDoctor);
		assertTrue("Created doctor's ID unknown", doctorId > 0);
		final Doctor doctor = daoDoctor.get(doctorId);
		assertNotNull(doctor);
		assertNotNull(doctor.getUser());
		assertEquals(doctorId, doctor.getId());
		assertEquals("test doctor", doctor.getNotes());
	}

	@Test
	public void testDeleteDoctor() {
		daoDoctor.delete(4);
		final Doctor doctor = daoDoctor.get(4);
		assertNull(doctor);
	}

	@Test
	public void testUpdateDoctor() {
		final Doctor updateDoctor = daoDoctor.get("updatedDoctor");
		assertNotNull(updateDoctor);
		updateDoctor.setNotes("updated doctor");
		updateDoctor.getUser().setPassword("newPass");
		daoDoctor.update(updateDoctor);
		Doctor doctor = daoDoctor.get(updateDoctor.getId());
		assertEquals(doctor.getNotes(), updateDoctor.getNotes());
	}

}
