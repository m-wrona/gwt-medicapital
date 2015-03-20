package com.medicapital.common.date;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.medicapital.common.entities.Day;

public class DayTimePeriodTest {

	private DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testContainsDate_RepeatableDay() {
		DayTimePeriod dayTimePeriod = new DayTimePeriod(Day.Monday, 8, 0, 16, 0);

		Date monday = new Date();
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 7, 59, 59);
		assertFalse(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 8, 0, 0);
		assertTrue(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 10, 30, 0);
		assertTrue(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 16, 0, 0);
		assertTrue(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 16, 0, 1);
		assertFalse(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 23);
		dateManager.setTimeStamp(monday, 10, 0, 0);
		assertFalse(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 8, 15, 0);
		assertTrue(dayTimePeriod.contains(monday));
	}

	@Test
	public void testContainsDate_SingleDay() {
		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		DayTimePeriod dayTimePeriod = new DayTimePeriod(mondayFrom, mondayTo);

		Date monday = new Date();
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 7, 59, 59);
		assertFalse(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 8, 0, 0);
		assertTrue(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 16, 0, 0);
		assertTrue(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 16, 0, 1);
		assertFalse(dayTimePeriod.contains(monday));
		dateManager.setDate(monday, 2011, 8, 23);
		dateManager.setTimeStamp(monday, 10, 0, 0);
		assertFalse(dayTimePeriod.contains(monday));
		Date nextMonday = new Date();
		dateManager.setDate(nextMonday, 2011, 8, 29);
		dateManager.setTimeStamp(nextMonday, 8, 0, 0);
		assertFalse(dayTimePeriod.contains(nextMonday));
		dateManager.setDate(monday, 2011, 8, 22);
		dateManager.setTimeStamp(monday, 8, 15, 0);
		assertTrue(dayTimePeriod.contains(monday));
	}

	@Test
	public void testCreateForDifferentDays() {
		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 23);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		try {
			new DayTimePeriod(mondayFrom, mondayTo);
			fail("Day time periods can be created for 2 days long time period");
		} catch (IllegalArgumentException e) {
			assertEquals("Dates must be from the same day - date from: Mon Aug 22 08:00:00 CEST 2011, date to: Tue Aug 23 16:00:00 CEST 2011", e.getMessage());
		}
	}

	@Test
	public void testContainsTimePeriod_RepeatableDay() {
		DayTimePeriod dayTimePeriod = new DayTimePeriod(Day.Monday, 8, 0, 16, 0);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 6, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 16, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 20, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 6, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 16, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 20, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));
		
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));
	}

	@Test
	public void testContainsTimePeriod_SingleDay() {
		Date singleMondayFrom = new Date();
		dateManager.setDate(singleMondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(singleMondayFrom, 8, 0, 0);
		Date singleMondayTo = new Date();
		dateManager.setDate(singleMondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(singleMondayTo, 16, 0, 0);
		DayTimePeriod dayTimePeriod = new DayTimePeriod(singleMondayFrom, singleMondayTo);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertTrue(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 6, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 16, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 20, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 6, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 16, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 20, 0, 0);
		assertFalse(dayTimePeriod.contains(mondayFrom, mondayTo));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));
	}

	@Test
	public void testContainsDayTimePeriod_RepeatableDay() {
		DayTimePeriod dayTimePeriod = new DayTimePeriod(Day.Monday, 8, 0, 16, 0);

		DayTimePeriod dayTime = new DayTimePeriod(Day.Monday, 8, 0, 16, 0);
		assertTrue(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 10, 0, 12, 0);
		assertTrue(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 8, 1, 16, 0);
		assertTrue(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 8, 0, 16, 1);
		assertFalse(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 7, 59, 16, 0);
		assertFalse(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 7, 59, 16, 1);
		assertFalse(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Tuesday, 10, 0, 12, 0);
		assertFalse(dayTimePeriod.contains(dayTime));
		dayTime = new DayTimePeriod(Day.Monday, 8, 0, 8, 15);
		assertTrue(dayTimePeriod.contains(dayTime));
	}

	@Test
	public void testContainsDayTimePeriod_SingleDay() {
		Date singleMondayFrom = new Date();
		dateManager.setDate(singleMondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(singleMondayFrom, 8, 0, 0);
		Date singleMondayTo = new Date();
		dateManager.setDate(singleMondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(singleMondayTo, 16, 0, 0);
		DayTimePeriod dayTimePeriod = new DayTimePeriod(singleMondayFrom, singleMondayTo);

		Date mondayFrom = new Date();
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		Date mondayTo = new Date();
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 16, 0, 0);
		assertTrue(dayTimePeriod.contains(new DayTimePeriod(mondayFrom, mondayTo)));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertTrue(dayTimePeriod.contains(new DayTimePeriod(mondayFrom, mondayTo)));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		dateManager.setDate(mondayFrom, 2011, 8, 29);
		dateManager.setTimeStamp(mondayFrom, 10, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 29);
		dateManager.setTimeStamp(mondayTo, 12, 0, 0);
		assertFalse(dayTimePeriod.contains(new DayTimePeriod(mondayFrom, mondayTo)));
		assertFalse(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));
		
		dateManager.setDate(mondayFrom, 2011, 8, 22);
		dateManager.setTimeStamp(mondayFrom, 8, 0, 0);
		dateManager.setDate(mondayTo, 2011, 8, 22);
		dateManager.setTimeStamp(mondayTo, 8, 15, 0);
		assertTrue(dayTimePeriod.contains(new DayTimePeriod(mondayFrom, mondayTo)));
		assertTrue(dayTimePeriod.contains(new TimePeriod(mondayFrom, mondayTo)));

		assertFalse(dayTimePeriod.contains(new DayTimePeriod(Day.Monday, 8, 0, 16, 0)));
	}
}
