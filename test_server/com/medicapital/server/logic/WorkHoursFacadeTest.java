package com.medicapital.server.logic;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import com.medicapital.server.database.DaoWorkHours;

public class WorkHoursFacadeTest {

	private WorkHoursFacade workHoursFacade;
	private DaoWorkHours daoWorkHours;

	@Before
	public void init() {
		workHoursFacade = new WorkHoursFacade();
		daoWorkHours = mock(DaoWorkHours.class);
		workHoursFacade.setDaoWorkHours(daoWorkHours);
	}

	@Test
	public void testGetDoctorWorkHours() {
		workHoursFacade.getDoctorWorkHours(1, null, null, 0, 10);
		verify(daoWorkHours).getDoctorWorkHours(1, null, null, 0, 10);
	}

	@Test
	public void testGetDoctorWorkHoursCount() {
		workHoursFacade.getDoctorWorkHoursCount(1, null, null);
		verify(daoWorkHours).getDoctorWorkHoursCount(1, null, null);
	}
}
