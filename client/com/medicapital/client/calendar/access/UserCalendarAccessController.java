package com.medicapital.client.calendar.access;

import static com.medicapital.client.calendar.access.CalendarEventAccessController.*;
import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import com.medicapital.client.lang.Lang;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.collections.WorkHoursMap;

/**
 * Controller controls action which can be performed on events and calendar. It
 * also decides how many data can be displayed.
 * 
 * @author mwronski
 * 
 */
public class UserCalendarAccessController {

	private WorkHoursMap workHoursMap;
	private int ownerId = -1;
	private int visitorId = -2;

	/**
	 * Filter data so proper amount of data will be shown for visitor
	 * 
	 * @param event
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final public <T extends CalendarEvent> T filter(T event) {
		dbg("filtering event: " + event);
		if (event instanceof PatientVisit) {
			return (T) filterPatientVisitData((PatientVisit) event);
		}
		return event;
	}

	/**
	 * Filter data in patient visit
	 * 
	 * @param patientVisit
	 * @return
	 */
	private PatientVisit filterPatientVisitData(PatientVisit patientVisit) {
		PatientVisit filterVisit = patientVisit.cloneEvent();
		filterVisit.setId(-1);
		filterVisit.setTitle(Lang.generic().patientVisit());
		filterVisit.setDescription(null);
		filterVisit.setEvaluation(null);
		filterVisit.setLocalization(null);
		filterVisit.setPatient(null);
		filterVisit.setDoctor(null);
		return filterVisit;
	}

	/**
	 * Check whether event can be added in given time tables
	 * 
	 * @param <T>
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public <T extends CalendarEvent> boolean canAdd(Date startTime, Date endTime) {
		return isVisitorOwner();
	}

	/**
	 * Check whether event can be added
	 * 
	 * @param <T>
	 * @param event
	 * @return
	 */
	public <T extends CalendarEvent> boolean canAdd(T event) {
		return !(event instanceof PatientVisit) && isVisitorOwner();
	}

	/**
	 * Check whether event can be updated
	 * 
	 * @param <T>
	 * @param event
	 * @return
	 */
	public <T extends CalendarEvent> boolean canUpdate(T event) {
		return !(event instanceof PatientVisit) && isVisitorOwner() && isEventEditable(event);
	}

	/**
	 * Check whether event can be deleted
	 * 
	 * @param <T>
	 * @param event
	 * @return
	 */
	public <T extends CalendarEvent> boolean canDelete(T event) {
		return !(event instanceof PatientVisit) && isVisitorOwner() && isEventEditable(event);
	}

	/**
	 * Check whether time of visit can be changed
	 * 
	 * @return
	 */
	public boolean canTimeTablesBeChanged(PatientVisit patientVisit) {
		return false;
	}

	/**
	 * Check whether time of event can be changed
	 * 
	 * @return
	 */
	final public boolean canTimeTablesBeChanged() {
		return isVisitorOwner();
	}

	/**
	 * Check whether evaluation can be added to visit
	 * 
	 * @return
	 */
	public boolean canEvaluationBeCreated(PatientVisit patientVisit) {
		if (patientVisit.getEvaluation() != null) {
			return false;
		}
		return isVisitorOwner() && !isEventEditable(patientVisit) && patientVisit.getPatient().getId() == ownerId;
	}

	/**
	 * Check whether evaluation can be displayed
	 * 
	 * @return
	 */
	public boolean canEvaluationBeDisplayed(PatientVisit visit) {
		if (visit.getEvaluation() == null) {
			return false;
		}
		return isVisitorOwner() && isVisitorVisit(visit);
	}

	final protected boolean isVisitorVisit(PatientVisit visit) {
		return visit.getPatient().getId() == visitorId || visit.getDoctor().getId() == visitorId;
	}

	/**
	 * Check whether patient visit can be displayed
	 * 
	 * @param event
	 * @return
	 */
	public boolean canDisplay(CalendarEvent event) {
		return isVisitorOwner();
	}

	final protected boolean isWorking(Date dateFrom, Date dateTo) {
		if (workHoursMap != null) {
			return workHoursMap.isWorking(dateFrom, dateTo);
		}
		return true;
	}

	final protected boolean isWorking(CalendarEvent event) {
		if (workHoursMap != null) {
			return workHoursMap.isWorking(event);
		}
		return true;
	}

	final public boolean isVisitorOwner() {
		return ownerId == visitorId;
	}

	final public void setOwnerId(int patientId) {
		this.ownerId = patientId;
	}

	final public int getOwnerId() {
		return ownerId;
	}

	final public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}

	final public int getVisitorId() {
		return visitorId;
	}

	final public void setWorkHoursMap(WorkHoursMap workHoursMap) {
		this.workHoursMap = workHoursMap;
	}

	final public WorkHoursMap getWorkHoursMap() {
		return workHoursMap;
	}

	final protected void dbg(String msg) {
		tracer(this).debug("[VisitorId: " + getVisitorId() + ", ownerId: " + getOwnerId() + "] " + msg);
	}

}
