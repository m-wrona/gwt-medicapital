package com.medicapital.common.entities;

import java.util.Date;

/**
 * Basic calendar event
 * 
 * @author mwronski
 * 
 */
public class CalendarEvent implements SerializableEntity {

	private long eventId = -1;

	private Date startTime = null;

	private Date endTime = null;

	private String title;

	private String description = null;

	private boolean wholeDay = false;

	private int ownerId;

	public CalendarEvent() {
		// empty
		eventId = new Date().getTime() % Integer.MAX_VALUE;
	}

	public CalendarEvent(final long eventId) {
		this.eventId = eventId;
	}

	@Override
	public void setId(final int entityId) {
		eventId = entityId;
	}

	@Override
	public int getId() {
		return (int) eventId;
	}

	public long getEventId() {
		return eventId;
	}

	public boolean isWholeDay() {
		return wholeDay;
	}

	public void setWholeDay(final boolean wholeDay) {
		this.wholeDay = wholeDay;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setOwnerId(final int ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	@Override
	public String toString() {
		final StringBuilder string = new StringBuilder('[');
		string.append("eventId:").append(eventId);
		string.append(", ownerId: ").append(ownerId);
		string.append(", startTime: ").append(startTime);
		string.append(", endTime: ").append(endTime);
		string.append(", wholeDay: ").append(wholeDay);
		string.append(", title: ").append(title);
		string.append(", description: ").append(description);
		string.append(']');
		return string.toString();
	}

	/**
	 * Clone data from this instance into clone instance
	 * 
	 * @param clone
	 */
	protected void cloneData(CalendarEvent source, final CalendarEvent target) {
		target.eventId = source.eventId;
		target.title = source.title;
		target.description = source.description;
		target.startTime = source.startTime;
		target.endTime = source.endTime;
		target.wholeDay = source.wholeDay;
		target.ownerId = source.ownerId;
	}

	public void rewriteData(CalendarEvent calendarEvent) {
		cloneData(calendarEvent, this);
	}

	/**
	 * Clone event instance
	 * 
	 * @return
	 */
	public CalendarEvent cloneEvent() {
		final CalendarEvent clone = new CalendarEvent();
		cloneData(this, clone);
		return clone;
	}

	@Override
	public boolean equals(final Object obj) {
		return EntitiesUtils.equals(this, obj);
	}

}
