package com.medicapital.server.database;

import java.util.Date;
import java.util.List;
import org.junit.Test;

import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.WorkHours;
import com.medicapital.server.database.hibernate.HibernateWorkHours;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.WorkHoursEntity;

public class DaoWorkHoursTest extends AbstractDBTestCase {

	private HibernateWorkHours daoWorkHours;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(WorkHoursEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		daoWorkHours = new HibernateWorkHours();
		daoWorkHours.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoWorkHours.close();
		daoWorkHours = null;
	}

	@Test
	public void testGetDoctorWeekWorkHours() {
		List<WorkHours> workHours = daoWorkHours.getDoctorWorkHours(1, null, null, 0, 10);
		assertNotNull(workHours);
		assertEquals(2, workHours.size());
		WorkHours tuesday = workHours.get(0);
		assertEquals(Day.Tuesday, tuesday.getDay());
		assertEquals(8, tuesday.getStartHour());
		assertEquals(0, tuesday.getStartMinutes());
		assertEquals(16, tuesday.getEndHour());
		assertEquals(0, tuesday.getEndMinutes());
		assertEquals(1, tuesday.getDoctorId());
		assertNotNull(tuesday.getLocalization());
		assertEquals(1, tuesday.getLocalization().getId());
		assertEquals(1, tuesday.getLocalization().getCityId());

		WorkHours monday = workHours.get(1);
		assertEquals(Day.Monday, monday.getDay());
		assertEquals(8, monday.getStartHour());
		assertEquals(0, monday.getStartMinutes());
		assertEquals(16, monday.getEndHour());
		assertEquals(0, monday.getEndMinutes());
		assertEquals(1, monday.getDoctorId());
		assertNotNull(monday.getLocalization());
		assertEquals(1, monday.getLocalization().getId());
		assertEquals(1, monday.getLocalization().getCityId());
	}

	@Test
	public void testGetDoctorWorkHoursCount() {
		assertEquals(2, daoWorkHours.getDoctorWorkHoursCount(1, null, null));
	}

	@Test
	public void testGetDoctorWorkHoursFreeDays() {
		DateManager dateManager = DateFactory.createDateManager();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		dateManager.setDate(dateFrom, 2010, 6, 1);
		dateManager.setDate(dateTo, 2010, 6, 14);
		List<WorkHours> workHours = daoWorkHours.getDoctorWorkHours(2, dateFrom, dateTo, 0, 10);
		assertNotNull(workHours);
		assertEquals(3, workHours.size());
		WorkHours tuesday = workHours.get(0);
		assertEquals(Day.Tuesday, tuesday.getDay());
		assertEquals(8, tuesday.getStartHour());
		assertEquals(0, tuesday.getStartMinutes());
		assertEquals(16, tuesday.getEndHour());
		assertEquals(0, tuesday.getEndMinutes());
		assertEquals(2, tuesday.getDoctorId());
		assertNotNull(tuesday.getLocalization());
		assertEquals(1, tuesday.getLocalization().getId());
		assertEquals(1, tuesday.getLocalization().getCityId());

		WorkHours monday = workHours.get(1);
		assertEquals(Day.Monday, monday.getDay());
		assertEquals(8, monday.getStartHour());
		assertEquals(0, monday.getStartMinutes());
		assertEquals(16, monday.getEndHour());
		assertEquals(0, monday.getEndMinutes());
		assertEquals(2, monday.getDoctorId());
		assertNotNull(monday.getLocalization());
		assertEquals(1, monday.getLocalization().getId());
		assertEquals(1, monday.getLocalization().getCityId());

		WorkHours freeDay = workHours.get(2);
		assertNull(freeDay.getDay());
		assertNotNull(freeDay.getDateFrom());
		assertNotNull(freeDay.getDateTo());
		assertEquals(0, freeDay.getStartHour());
		assertEquals(0, freeDay.getStartMinutes());
		assertEquals(0, freeDay.getEndHour());
		assertEquals(0, freeDay.getEndMinutes());
		assertEquals(2, freeDay.getDoctorId());
		assertNotNull(freeDay.getLocalization());
		assertEquals(1, freeDay.getLocalization().getId());
		assertEquals(1, freeDay.getLocalization().getCityId());
	}

	@Test
	public void testGetDoctorHoursCountFreeDays() {
		DateManager dateManager = DateFactory.createDateManager();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		dateManager.setDate(dateFrom, 2010, 6, 1);
		dateManager.setDate(dateTo, 2010, 6, 14);
		assertEquals(3, daoWorkHours.getDoctorWorkHoursCount(2, dateFrom, dateTo));
	}

	@Test
	public void testCreateWorkHours() {
		WorkHours workHours = new WorkHours();
		workHours.setDoctorId(1);
		workHours.setStartHour(8);
		workHours.setEndHour(12);
		int newEntityId = daoWorkHours.create(workHours);
		assertTrue("ID of new entity is unknown", newEntityId > 0);
		WorkHours createdWorkHours = daoWorkHours.get(newEntityId);
		assertNotNull(createdWorkHours);
	}

	@Test
	public void testCreateWorkHoursWithLocalization() {
		WorkHours workHours = new WorkHours();
		workHours.setDoctorId(1);
		workHours.setStartHour(8);
		workHours.setEndHour(12);
		Localization localization = new Localization();
		workHours.setLocalization(localization);
		localization.setCityId(1);
		localization.setAddress("test address");
		int newEntityId = daoWorkHours.create(workHours);
		assertTrue("ID of new entity is unknown", newEntityId > 0);
		WorkHours createdWorkHours = daoWorkHours.get(newEntityId);
		assertNotNull(createdWorkHours);
		assertNotNull(workHours.getLocalization());
	}

	@Test
	public void testUpdateWorkHours() {
		WorkHours workHours = daoWorkHours.get(6);
		workHours.setStartHour(2);
		workHours.setEndHour(4);
		workHours.setDay(Day.Sunday);
		daoWorkHours.update(workHours);
		WorkHours updatedWorkHours = daoWorkHours.get(6);
		assertEquals(Day.Sunday, updatedWorkHours.getDay());
		assertNotNull(updatedWorkHours.getDateFrom());
		assertNotNull(updatedWorkHours.getDateTo());
		assertEquals(2, updatedWorkHours.getStartHour());
		assertEquals(0, updatedWorkHours.getStartMinutes());
		assertEquals(4, updatedWorkHours.getEndHour());
		assertEquals(0, updatedWorkHours.getEndMinutes());
		assertEquals(3, updatedWorkHours.getDoctorId());
		assertNotNull(updatedWorkHours.getLocalization());
		assertEquals(1, updatedWorkHours.getLocalization().getId());
		assertEquals(1, updatedWorkHours.getLocalization().getCityId());
	}

	@Test
	public void testDeleteWorkHours() {
		assertNotNull(daoWorkHours.get(7));
		daoWorkHours.delete(7);
		assertNull(daoWorkHours.get(7));
	}
}
