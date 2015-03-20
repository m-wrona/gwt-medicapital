package com.medicapital.common.date;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Day;

public class DateManagerTest {

	private final DateManager dateManager = DateFactory.createDateManager();

	@Test
	public void testSetDate() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		assertEquals("Sun Jan 02 00:00:00 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(2, dateManager.getMonthDay(date));
	}

	@Test
	public void testGetWeekDay() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		assertEquals("Sun Jan 02 00:00:00 CET 2000", date.toString());
		assertEquals(Day.Sunday, dateManager.getWeekDay(date));
	}

	@Test
	public void testMoveToNextDay() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		assertEquals("Sun Jan 02 00:00:00 CET 2000", date.toString());
		assertEquals(Day.Sunday, dateManager.getWeekDay(date));
		dateManager.moveToNextDay(date, Day.Monday);
		assertEquals("Mon Jan 03 00:00:00 CET 2000", date.toString());
		assertEquals(Day.Monday, dateManager.getWeekDay(date));
	}

	@Test
	public void testMoveToPreviousDay() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		assertEquals("Sun Jan 02 00:00:00 CET 2000", date.toString());
		assertEquals(Day.Sunday, dateManager.getWeekDay(date));
		dateManager.moveToPreviousDay(date, Day.Saturday);
		assertEquals("Sat Jan 01 00:00:00 CET 2000", date.toString());
		assertEquals(Day.Saturday, dateManager.getWeekDay(date));
	}

	@Test
	public void testSetTimeStamp() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 1);
		dateManager.setTimeStamp(date, 1, 2, 3);
		assertEquals("Sat Jan 01 01:02:03 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(1, dateManager.getMonthDay(date));
		assertEquals(1, dateManager.getHours(date));
		assertEquals(2, dateManager.getMinutes(date));
	}

	@Test
	public void testClearTimeStamp() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 1);
		dateManager.setTimeStamp(date, 1, 2, 3);
		assertEquals("Sat Jan 01 01:02:03 CET 2000", date.toString());
		dateManager.clearTimeStamp(date);
		assertEquals("Sat Jan 01 00:00:00 CET 2000", date.toString());
	}

	@Test
	public void testAfter() {
		Date before = new Date();
		dateManager.setDate(before, 2000, 1, 1);
		Date after = new Date();
		dateManager.setDate(after, 2000, 1, 2);
		assertTrue(dateManager.after(after, before));
		assertFalse(dateManager.after(before, after));
	}

	@Test
	public void testBefore() {
		Date before = new Date();
		dateManager.setDate(before, 2000, 1, 1);
		Date after = new Date();
		dateManager.setDate(after, 2000, 1, 2);
		assertFalse(dateManager.before(after, before));
		assertTrue(dateManager.before(before, after));
	}

	@Test
	public void testAddDays() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		assertEquals("Sun Jan 02 00:00:00 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(2, dateManager.getMonthDay(date));
		dateManager.addDays(date, 10);
		assertEquals("Wed Jan 12 00:00:00 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(12, dateManager.getMonthDay(date));
	}

	@Test
	public void testAddMinutes() {
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		dateManager.setTimeStamp(date, 23, 59, 20);
		assertEquals("Sun Jan 02 23:59:20 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(2, dateManager.getMonthDay(date));
		dateManager.addMinutes(date, 1);
		assertEquals("Mon Jan 03 00:00:20 CET 2000", date.toString());
		assertEquals(2000, dateManager.getYear(date));
		assertEquals(1, dateManager.getMonth(date));
		assertEquals(3, dateManager.getMonthDay(date));
	}

	@Test
	public void testCompareDates() {
		Date before = new Date();
		dateManager.setDate(before, 2000, 1, 1);
		Date date = new Date();
		dateManager.setDate(date, 2000, 1, 2);
		Date after = new Date();
		dateManager.setDate(after, 2000, 1, 3);

		assertEquals(0, dateManager.compare(date, date));
		assertEquals(-1, dateManager.compare(date, after));
		assertEquals(1, dateManager.compare(date, before));
	}

	@Test
	public void testCompareDatesWithoutTimeStampt() {
		Date date1 = new Date();
		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertEquals(0, dateManager.compareWithoutTimeStamps(date1, date2));

		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 2, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertEquals(-1, dateManager.compareWithoutTimeStamps(date1, date2));

		dateManager.setDate(date1, 2000, 2, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertEquals(1, dateManager.compareWithoutTimeStamps(date1, date2));
	}
	
	@Test
	public void testAfterEqulas() {
		Date date1 = new Date();
		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 8, 0, 0);
		assertTrue(dateManager.afterEqaul(date1, date2));

		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 2, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertFalse(dateManager.afterEqaul(date1, date2));

		dateManager.setDate(date1, 2000, 2, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertTrue(dateManager.afterEqaul(date1, date2));
	}
	
	@Test
	public void testBeforeEqulas() {
		Date date1 = new Date();
		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		Date date2 = new Date();
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 8, 0, 0);
		assertTrue(dateManager.beforeEqual(date1, date2));

		dateManager.setDate(date1, 2000, 1, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 2, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertTrue(dateManager.beforeEqual(date1, date2));

		dateManager.setDate(date1, 2000, 2, 1);
		dateManager.setTimeStamp(date1, 8, 0, 0);
		dateManager.setDate(date2, 2000, 1, 1);
		dateManager.setTimeStamp(date2, 9, 0, 0);
		assertFalse(dateManager.beforeEqual(date1, date2));
	}

}
