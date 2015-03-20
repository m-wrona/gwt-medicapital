package com.medicapital.client.pages.doctor;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import java.util.Map;
import com.medicapital.client.calendar.CalendarPresenter;
import com.medicapital.client.calendar.WeekView;
import com.medicapital.client.calendar.access.UserCalendarAccessController;
import com.medicapital.client.calendar.access.DoctorCalendarAccessController;
import com.medicapital.client.doctor.DoctorFactory;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.UserPage;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.util.MapUtils;

/**
 * Presenter of doctor's calendar. Page can be visited both by doctors and
 * users. That's is why class extends {@link UserPage} and that's why displayed
 * data must be controlled.
 * 
 * @author mwronski
 * 
 */
final public class DoctorCalendarPage extends UserPage<DoctorCalendarPageForm> {

	public static final String PARAM_DOCTOR_ID = "DoctorId";
	private final DoctorFactory doctorFactory = new DoctorFactory();
	private CalendarPresenter calendarPresenter;

	@Override
	protected DoctorCalendarPageForm createPageView() {
		return new DoctorCalendarPageForm();
	}

	@Override
	protected void initPage(DoctorCalendarPageForm pageView) {
		calendarPresenter = doctorFactory.createCalendarPresenter(pageView.getCalendar());
	}

	@Override
	final public void loadState(Map<String, String> pageStateParameters) {
		final int doctorId = MapUtils.getInt(pageStateParameters, PARAM_DOCTOR_ID, false);
		int patientId = -1;
		// set calendar access controller
		UserCalendarAccessController doctorCalendarAccessController = new DoctorCalendarAccessController();
		calendarPresenter.setDataAccessController(doctorCalendarAccessController);
		calendarPresenter.getCalendarEventDisplayer().setDataAccessController(doctorCalendarAccessController);
		if (doctorId == getLoginData().getId() && getLoginData().getRole() == UserRole.Doctor) {
			// owner of calendar
			tracer(this).debug("Current logged user is owner of calendar(doctor)");
			doctorCalendarAccessController.setVisitorId(doctorId);
		} else {
			// visitor of calendar
			tracer(this).debug("Current logged user is patient(visitor)");
			patientId = getLoginData().getId();
			doctorCalendarAccessController.setVisitorId(patientId);
		}
		// set doctor and patient ID
		calendarPresenter.getCalendarEventDisplayer().setDoctorId(doctorId);
		calendarPresenter.getCalendarEventDisplayer().setPatientId(patientId);
		calendarPresenter.getCommandFactory().setDoctorId(doctorId);
		calendarPresenter.getCommandFactory().setPatientId(patientId);
		doctorCalendarAccessController.setOwnerId(doctorId);
		// display today
		calendarPresenter.getView().initView(new Date(), WeekView.Week, 0, 24, 4);
	}

	public static DisplayPageEvent createPageEvent(final Object sender, final int doctorId) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, DoctorCalendarPage.class);
		pageEvent.addRequestParameter(PARAM_DOCTOR_ID, "" + doctorId);
		return pageEvent;
	}

}
