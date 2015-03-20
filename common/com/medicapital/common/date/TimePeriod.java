package com.medicapital.common.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Class holds time period which is expressed by date from to date to values.
 * Period can be iterated by day time periods.
 * 
 * @author mwronski
 * 
 */
final public class TimePeriod implements Iterable<DayTimePeriod> {

	private static final DateManager dateManager = DateFactory.createDateManager();
	private final Date dateFrom;
	private final Date dateTo;

	public TimePeriod(Date dateFrom, Date dateTo) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	/**
	 * Check whether date is between this time period.
	 * 
	 * @param date
	 * @return
	 */
	public boolean contains(Date date) {
		return dateManager.afterEqaul(date, dateFrom) && dateManager.beforeEqual(date, dateTo);
	}

	/**
	 * Check whether time period is inside this time period
	 * 
	 * @param timePeriod
	 * @return
	 */
	public boolean contains(TimePeriod timePeriod) {
		return contains(timePeriod.getDateFrom()) && contains(timePeriod.getDateTo());
	}

	public boolean contains(DayTimePeriod dayTimePeriod) {
		if (dayTimePeriod.getDayDate() == null) {
			throw new IllegalArgumentException("Day date not set in day time period: " + dayTimePeriod);
		}
		Date dateFrom = new Date(dayTimePeriod.getDayDate().getTime());
		dateManager.setTimeStamp(dateFrom, dayTimePeriod.getStartHour(), dayTimePeriod.getStartMinutes(), 0);
		Date dateTo = new Date(dayTimePeriod.getDayDate().getTime());
		dateManager.setTimeStamp(dateTo, dayTimePeriod.getEndHour(), dayTimePeriod.getEndMinutes(), 0);
		return contains(dateFrom) && contains(dateTo);
	}

	@Override
	public Iterator<DayTimePeriod> iterator() {
		List<DayTimePeriod> dayTimePeriods = toDayTimePeriods();
		return dayTimePeriods.iterator();
	}

	private List<DayTimePeriod> toDayTimePeriods() {
		List<DayTimePeriod> dayTimePeriods = new ArrayList<DayTimePeriod>();
		Date currDateFrom = new Date(dateFrom.getTime());
		Date currDateTo = null;
		do {
			currDateTo = new Date(currDateFrom.getTime());
			dateManager.clearTimeStamp(currDateTo);
			dateManager.addDays(currDateTo, 1);
			dateManager.addMinutes(currDateTo, -1);
			if (currDateTo.after(dateTo)) {
				currDateTo = new Date(dateTo.getTime());
			}
			dayTimePeriods.add(new DayTimePeriod(currDateFrom, currDateTo));
			currDateFrom = new Date(currDateFrom.getTime());
			dateManager.clearTimeStamp(currDateFrom);
			dateManager.addDays(currDateFrom, 1);
		} while (currDateTo.before(dateTo));
		return dayTimePeriods;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

}
