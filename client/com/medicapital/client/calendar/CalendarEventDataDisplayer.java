package com.medicapital.client.calendar;

import java.util.Date;
import com.medicapital.client.calendar.access.UserCalendarAccessController;
import com.medicapital.client.evaluation.CreateVisitEvaluationForm;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenterView;
import com.medicapital.client.evaluation.VisitEvaluationForm;
import com.medicapital.client.evaluation.VisitEvaluationPresenterView;
import com.medicapital.client.user.SearchUserForm;
import com.medicapital.client.visit.CreateVisitPresenter;
import com.medicapital.client.visit.CreateVisitPresenterView;
import com.medicapital.client.visit.EditVisitForm;
import com.medicapital.client.visit.EditVisitPresenter;
import com.medicapital.client.visit.EditVisitView;
import com.medicapital.client.visit.NewVisitForm;
import com.medicapital.client.visit.VisitFactory;
import com.medicapital.client.visit.VisitForm;
import com.medicapital.client.visit.VisitPresenter;
import com.medicapital.client.visit.VisitPresenterView;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;

/**
 * Displayer controls what views and are displayed for proper calendar events.
 * 
 * @author mwronski
 * 
 */
public class CalendarEventDataDisplayer {

	private final VisitFactory visitFactory = new VisitFactory();
	private UserCalendarAccessController dataAccessController;
	private int patientId = -1;
	private int doctorId = -2;

	/**
	 * Display view for adding new event
	 * 
	 * @param startTime
	 * @param endTime
	 */
	public void displayNewEventView(final Date startTime, Date endTime) {
		validate();
		CreateVisitPresenterView createVisitView = new NewVisitForm();
		createVisitView.setShowAsDialogBox(true);
		SearchUserForm searchUserForm = new SearchUserForm();
		searchUserForm.setShowAsDialogBox(true);
		createVisitView.setVisitTimeEditable(dataAccessController.canTimeTablesBeChanged());
		final CreateVisitPresenter visitPresenter = visitFactory.createCreateVisitPresenter(createVisitView, searchUserForm);
		visitPresenter.init(doctorId, patientId, startTime);
		createVisitView.setViewVisible(true);
	}

	/**
	 * Display event view. View will be chosen based on whether event can still
	 * be edited or not.
	 * 
	 * @param <T>
	 * @param calendarEvent
	 */
	public <T extends CalendarEvent> void displayEventView(final T calendarEvent) {
		validate();
		if (calendarEvent instanceof PatientVisit) {
			PatientVisit visit = (PatientVisit) calendarEvent;
			final VisitPresenterView visitView = new VisitForm();
			visitView.setShowAsDialogBox(true);
			final VisitEvaluationPresenterView evaluationView = new VisitEvaluationForm();
			evaluationView.setShowAsDialogBox(true);
			final CreateVisitEvaluationPresenterView createEvaluationView = new CreateVisitEvaluationForm();
			createEvaluationView.setShowAsDialogBox(true);
			final VisitPresenter visitPresenter = visitFactory.createVisitPresenter(visitView, evaluationView, createEvaluationView);
			visitPresenter.display(visit);
			visitView.setCreateEvaluationVisible(dataAccessController.canEvaluationBeCreated(visit));
			visitView.setDisplayEvaluationVisible(dataAccessController.canEvaluationBeDisplayed(visit));
			visitView.setViewVisible(true);
		}
	}

	public <T extends CalendarEvent> void displayEditEventView(final T calendarEvent) {
		validate();
		if (calendarEvent instanceof PatientVisit) {
			PatientVisit visit = (PatientVisit) calendarEvent;
			final EditVisitView editVisitView = new EditVisitForm();
			editVisitView.setShowAsDialogBox(true);
			editVisitView.setVisitTimeEditable(dataAccessController.canTimeTablesBeChanged(visit));
			final EditVisitPresenter editVisitPresenter = visitFactory.createEditVisitPresenter(editVisitView);
			editVisitView.setCalendarEvent(visit);
			editVisitView.display();
			editVisitPresenter.display(visit);
			editVisitPresenter.getVisitView().setViewVisible(true);
		}
	}

	/**
	 * Validate state
	 */
	private void validate() {
		if (dataAccessController == null) {
			throw new IllegalArgumentException("Calendar access controller not set");
		}
	}

	public void setDoctorId(final int doctorId) {
		this.doctorId = doctorId;
	}

	public void setPatientId(final int userId) {
		patientId = userId;
	}

	public void setDataAccessController(UserCalendarAccessController dataAccessController) {
		this.dataAccessController = dataAccessController;
	}

	public UserCalendarAccessController getDataAccessController() {
		return dataAccessController;
	}

}
