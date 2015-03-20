package com.medicapital.client.calendar.access;

import static com.medicapital.client.calendar.access.CalendarEventAccessController.*;
import java.util.Date;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;

final public class DoctorCalendarAccessController extends UserCalendarAccessController {

	/**
	 * Check whether patient visit can be displayed
	 * 
	 * @param event
	 * @return
	 */
	public boolean canDisplay(CalendarEvent event) {
		dbg("can display: " + event);
		boolean canDisplay = false;
		if (event.getId() < 0) {
			canDisplay = false;
		} else if (event instanceof PatientVisit) {
			PatientVisit visit = (PatientVisit) event;
			canDisplay = isVisitorVisit(visit);
		} else {
			canDisplay = isVisitorOwner();
		}
		dbg("can display - return: " + canDisplay);
		return canDisplay;
	}

	@Override
	public <T extends CalendarEvent> boolean canAdd(Date startTime, Date endTime) {
		dbg("can add - startTime: " + startTime + ", endTime: " + endTime);
		boolean canAdd = false;
		if (startTime.after(new Date())) {
			canAdd = isVisitorOwner() ? true : isWorking(startTime, endTime);
		} else {
			UIUtil.alert(Lang.warnings().eventInPastCantBeAdded());
			canAdd = false;
		}
		dbg("can add - return: " + canAdd);
		return canAdd;
	}

	@Override
	public <T extends CalendarEvent> boolean canAdd(T event) {
		dbg("can add: " + event);
		if (!canModifyEvent(event, false)) {
			return false;
		}
		return isVisitorOwner() ? true : isWorking(event);
	}

	@Override
	public <T extends CalendarEvent> boolean canUpdate(T event) {
		dbg("can update: " + event);
		return canModifyEvent(event, false);
	}

	@Override
	public <T extends CalendarEvent> boolean canDelete(T event) {
		dbg("can delete: " + event);
		return canModifyEvent(event, true);
	}

	private <T extends CalendarEvent> boolean canModifyEvent(T event, boolean showErrorMsg) {
		boolean canModify = false;
		if (event.getId() < 0) {
			canModify = false;
		} else if (!isEventEditable(event)) {
			if (showErrorMsg) {
				UIUtil.alert(Lang.warnings().eventInPastCantBeAdded());
			}
			canModify = false;
		} else if (event instanceof PatientVisit) {
			PatientVisit visit = (PatientVisit) event;
			canModify = isVisitorVisit(visit);
		} else {
			canModify = isVisitorOwner();
		}
		dbg("can modify - return: " + canModify);
		return canModify;
	}

	@Override
	public boolean canTimeTablesBeChanged(PatientVisit patientVisit) {
		return isVisitorOwner() && isEventEditable(patientVisit);
	}

	@Override
	public boolean canEvaluationBeDisplayed(PatientVisit visit) {
		if (visit.getEvaluation() == null) {
			return false;
		}
		return isVisitorVisit(visit);
	}

	@Override
	public boolean canEvaluationBeCreated(PatientVisit patientVisit) {
		if (patientVisit.getEvaluation() != null) {
			return false;
		}
		return !isVisitorOwner() && isVisitorVisit(patientVisit) && !isEventEditable(patientVisit);
	}
}
