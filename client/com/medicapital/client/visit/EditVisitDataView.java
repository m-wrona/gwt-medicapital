package com.medicapital.client.visit;

import java.util.Set;
import com.medicapital.client.calendar.CalendarEventView;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.VisitType;

/**
 * View for editing visit
 * 
 * @author mwronski
 * 
 */
interface EditVisitDataView extends SetterVisitView, GetterVisitView, CalendarEventView<PatientVisit> {

	/**
	 * Set doctor's visit types
	 * 
	 * @param visitTypes
	 */
	void setVisitTypes(Set<VisitType> visitTypes);

	/**
	 * Set visit duration time
	 * 
	 * @param time
	 */
	void setDurationTime(int time);

	/**
	 * Set whether time tables of visit can be changed
	 * 
	 * @param enabled
	 */
	void setVisitTimeEditable(boolean enabled);

	/**
	 * Set message visible that booking is on-going
	 * 
	 * @param visible
	 */
	void setBookingOnGoinMsgVisible(boolean visible);

	/**
	 * Set message visible that booking was successful
	 * 
	 * @param visible
	 */
	void setBookingOkVisible(boolean visible);

	/**
	 * Set message visible that booking was unsuccessful
	 * 
	 * @param visible
	 */
	void setBookingErrorVisible(boolean visible);

}
