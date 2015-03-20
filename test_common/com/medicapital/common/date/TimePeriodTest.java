package com.medicapital.common.date;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import com.medicapital.common.entities.Day;

public class TimePeriodTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testContainsDate_TheSameDay() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 1);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 1);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		Date date = new Date(dateFrom.getTime());
		dateManager.setTimeStamp(date, 0, 0, 0);
		assertFalse(timePeriod.contains(date));
		dateManager.setTimeStamp(date, 7, 59, 0);
		assertFalse(timePeriod.contains(date));
		dateManager.setTimeStamp(date, 8, 00, 0);
		assertTrue(timePeriod.contains(date));
		dateManager.setTimeStamp(date, 8, 01, 0);
		assertTrue(timePeriod.contains(date));
		dateManager.setTimeStamp(date, 16, 00, 0);
		assertTrue(timePeriod.contains(date));
		dateManager.setTimeStamp(date, 16, 01, 0);
		assertFalse(timePeriod.contains(date));
	}

	@Test
	public void testContainsDate_MultiDay() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 1);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 2);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		Date date = new Date();
		dateManager.setDate(date, 2011, 1, 1);
		dateManager.setTimeStamp(date, 8, 01, 0);
		assertTrue(timePeriod.contains(date));
		dateManager.setDate(date, 2011, 1, 1);
		dateManager.setTimeStamp(date, 7, 59, 59);
		assertFalse(timePeriod.contains(date));
		dateManager.setDate(date, 2011, 1, 2);
		dateManager.setTimeStamp(date, 16, 0, 0);
		assertTrue(timePeriod.contains(date));
		dateManager.setDate(date, 2011, 1, 2);
		dateManager.setTimeStamp(date, 16, 0, 1);
		assertFalse(timePeriod.contains(date));
	}

	@Test
	public void testContainsTimePeriod_Before() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 16);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		assertTrue(timePeriod.contains(timePeriod));

		Date date1 = new Date();
		dateManager.setDate(date1, 2011, 1, 14);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2011, 1, 15);
		dateManager.setTimeStamp(date2, 7, 0, 0);
		TimePeriod timePeriod1 = new TimePeriod(date1, date2);

		assertFalse(timePeriod.contains(timePeriod1));
		assertFalse(timePeriod1.contains(timePeriod));
	}

	@Test
	public void testContainsTimePeriod_BeforePart() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 16);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		assertTrue(timePeriod.contains(timePeriod));

		Date date1 = new Date();
		dateManager.setDate(date1, 2011, 1, 14);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2011, 1, 16);
		dateManager.setTimeStamp(date2, 16, 0, 0);
		TimePeriod timePeriod1 = new TimePeriod(date1, date2);

		assertFalse(timePeriod.contains(timePeriod1));
		assertTrue(timePeriod1.contains(timePeriod));
	}

	@Test
	public void testContainsTimePeriod_After() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 16);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		assertTrue(timePeriod.contains(timePeriod));

		Date date1 = new Date();
		dateManager.setDate(date1, 2011, 1, 16);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2011, 1, 17);
		dateManager.setTimeStamp(date2, 7, 0, 0);
		TimePeriod timePeriod1 = new TimePeriod(date1, date2);

		assertFalse(timePeriod.contains(timePeriod1));
		assertFalse(timePeriod1.contains(timePeriod));
	}

	@Test
	public void testContainsTimePeriod_Inside() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 16);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		assertTrue(timePeriod.contains(timePeriod));

		Date date1 = new Date();
		dateManager.setDate(date1, 2011, 1, 15);
		dateManager.setTimeStamp(date1, 10, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2011, 1, 16);
		dateManager.setTimeStamp(date2, 15, 0, 0);
		TimePeriod timePeriod1 = new TimePeriod(date1, date2);

		assertTrue(timePeriod.contains(timePeriod1));
		assertFalse(timePeriod1.contains(timePeriod));
	}

	@Test
	public void testTimePeriodToDayTimePeriod_TheSameDay() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 15);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);

		Iterator<DayTimePeriod> dayTimePeriods = timePeriod.iterator();
		assertTrue(dayTimePeriods.hasNext());
		DayTimePeriod day15 = dayTimePeriods.next();
		assertNotNull(day15);
		assertEquals("Sat Jan 15 00:00:00 CET 2011", day15.getDayDate().toString());
		assertEquals(Day.Saturday, day15.getDay());
		assertEquals(8, day15.getStartHour());
		assertEquals(0, day15.getStartMinutes());
		assertEquals(16, day15.getEndHour());
		assertEquals(0, day15.getEndMinutes());

		assertFalse(dayTimePeriods.hasNext());
	}

	@Test
	public void testTimePeriodToDayTimePeriod_MultiDay() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 17);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);

		Iterator<DayTimePeriod> dayTimePeriods = timePeriod.iterator();
		assertTrue(dayTimePeriods.hasNext());
		DayTimePeriod day15 = dayTimePeriods.next();
		assertNotNull(day15);
		assertEquals("Sat Jan 15 00:00:00 CET 2011", day15.getDayDate().toString());
		assertEquals(Day.Saturday, day15.getDay());
		assertEquals(8, day15.getStartHour());
		assertEquals(0, day15.getStartMinutes());
		assertEquals(23, day15.getEndHour());
		assertEquals(59, day15.getEndMinutes());

		assertTrue(dayTimePeriods.hasNext());
		DayTimePeriod day16 = dayTimePeriods.next();
		assertNotNull(day16);
		assertEquals("Sun Jan 16 00:00:00 CET 2011", day16.getDayDate().toString());
		assertEquals(Day.Sunday, day16.getDay());
		assertEquals(0, day16.getStartHour());
		assertEquals(0, day16.getStartMinutes());
		assertEquals(23, day16.getEndHour());
		assertEquals(59, day16.getEndMinutes());

		assertTrue(dayTimePeriods.hasNext());
		DayTimePeriod day17 = dayTimePeriods.next();
		assertNotNull(day17);
		assertEquals("Mon Jan 17 00:00:00 CET 2011", day17.getDayDate().toString());
		assertEquals(Day.Monday, day17.getDay());
		assertEquals(0, day17.getStartHour());
		assertEquals(0, day17.getStartMinutes());
		assertEquals(16, day17.getEndHour());
		assertEquals(0, day17.getEndMinutes());

		assertFalse(dayTimePeriods.hasNext());
	}
	
	@Test
	public void testContainsDayTimePeriod() {
		Date dateFrom = new Date();
		dateManager.setDate(dateFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dateFrom, 8, 0, 0);
		Date dateTo = new Date();
		dateManager.setDate(dateTo, 2011, 1, 16);
		dateManager.setTimeStamp(dateTo, 16, 0, 0);
		TimePeriod timePeriod = new TimePeriod(dateFrom, dateTo);
		assertTrue(timePeriod.contains(timePeriod));

		Date dayFrom = new Date();
		dateManager.setDate(dayFrom, 2011, 1, 15);
		dateManager.setTimeStamp(dayFrom, 8, 0, 0);
		Date dayTo = new Date();
		dateManager.setDate(dayTo, 2011, 1, 15);
		dateManager.setTimeStamp(dayTo, 16, 0, 0);
		assertTrue(timePeriod.contains(new DayTimePeriod(dayFrom, dayTo)));
		
		dateManager.setDate(dayFrom, 2011, 1, 16);
		dateManager.setTimeStamp(dayFrom, 8, 0, 0);
		dateManager.setDate(dayTo, 2011, 1, 16);
		dateManager.setTimeStamp(dayTo, 9, 0, 0);
		assertTrue(timePeriod.contains(new DayTimePeriod(dayFrom, dayTo)));
		
		dateManager.setDate(dayFrom, 2011, 1, 17);
		dateManager.setTimeStamp(dayFrom, 8, 0, 0);
		dateManager.setDate(dayTo, 2011, 1, 17);
		dateManager.setTimeStamp(dayTo, 16, 0, 0);
		assertFalse(timePeriod.contains(new DayTimePeriod(dayFrom, dayTo)));
	}
}
