package com.medicapital.common.entities.collections;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.WorkHours;
import com.medicapital.common.entities.collections.WorkHoursMap;
import com.medicapital.common.testutil.TestDateFormatter;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DateFactory.class, DateFormatter.class })
public class WorkHoursMapTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Before
	public void before() {
		mockDateFormatter();
	}

	private void mockDateFormatter() {
		try {
			PowerMockito.mockStatic(DateFactory.class);
			DateFormatter mockDateFormatter = new TestDateFormatter();
			when(DateFactory.createDateFormatter()).thenReturn(mockDateFormatter);
			when(DateFactory.createDateManager()).thenReturn(dateManager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testIsWorking_OneDayOnePeriod() {
		WorkHoursMap workHoursMap = new WorkHoursMap();
		WorkHours mondayWorkHours = new WorkHours();
		mondayWorkHours.setDay(Day.Monday);
		mondayWorkHours.setStartHour(8);
		mondayWorkHours.setEndHour(16);
		workHoursMap.add(mondayWorkHours);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 7, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 15, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 15, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));
	}

	@Test
	public void testIsWorking_OneDayManyPeriods() {
		WorkHoursMap workHoursMap = new WorkHoursMap();
		WorkHours mondayWorkHours = new WorkHours();
		mondayWorkHours.setDay(Day.Monday);
		mondayWorkHours.setStartHour(8);
		mondayWorkHours.setEndHour(9);
		workHoursMap.add(mondayWorkHours);
		WorkHours mondayWorkHours2 = new WorkHours();
		mondayWorkHours2.setDay(Day.Monday);
		mondayWorkHours2.setStartHour(15);
		mondayWorkHours2.setEndHour(16);
		workHoursMap.add(mondayWorkHours2);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 7, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 15, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 15, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));
	}

	@Test
	public void testIsWorking_OneDayPeriodsNextToEachOther() {
		WorkHoursMap workHoursMap = new WorkHoursMap();
		WorkHours mondayWorkHours = new WorkHours();
		mondayWorkHours.setDay(Day.Monday);
		mondayWorkHours.setStartHour(8);
		mondayWorkHours.setEndHour(9);
		workHoursMap.add(mondayWorkHours);
		WorkHours mondayWorkHours2 = new WorkHours();
		mondayWorkHours2.setDay(Day.Monday);
		mondayWorkHours2.setStartHour(9);
		mondayWorkHours2.setEndHour(10);
		workHoursMap.add(mondayWorkHours2);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 7, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 9, 45, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 10, 0, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 10, 0, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));
	}

	@Test
	public void testIsWorking_SpecialEvents() {
		WorkHoursMap workHoursMap = new WorkHoursMap();
		WorkHours mondayWorkHours = new WorkHours();
		mondayWorkHours.setDay(Day.Monday);
		mondayWorkHours.setStartHour(8);
		mondayWorkHours.setEndHour(9);
		workHoursMap.add(mondayWorkHours);
		WorkHours specialWorkHours = new WorkHours();
		Date monday29 = new Date();
		dateManager.setDate(monday29, 2011, 8, 29);
		specialWorkHours.setDateFrom(monday29);
		specialWorkHours.setDateTo(monday29);
		specialWorkHours.setStartHour(10);
		specialWorkHours.setEndHour(11);
		workHoursMap.add(specialWorkHours);
		WorkHours freeDay = new WorkHours();
		Date monday5 = new Date();
		dateManager.setDate(monday5, 2011, 9, 5);
		freeDay.setDateFrom(monday5);
		freeDay.setDateTo(monday5);
		workHoursMap.add(freeDay);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 10, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 29);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 29);
		dateManager.setTimeStamp(mondayTo, 10, 15, 0);
		assertTrue(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 9, 5);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 9, 5);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));

		mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 9, 5);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 9, 5);
		dateManager.setTimeStamp(mondayTo, 10, 15, 0);
		assertFalse(workHoursMap.isWorking(mondayFrom, mondayTo));
	}
}
