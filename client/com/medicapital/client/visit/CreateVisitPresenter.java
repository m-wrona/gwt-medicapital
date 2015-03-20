package com.medicapital.client.visit;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.Callback;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.user.SearchUserPresenter;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.commands.visit.LockVisitCommand;
import com.medicapital.common.commands.visit.BookVisitCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.VisitType;

public class CreateVisitPresenter extends CreateEntityPresenter<PatientVisit> {

	private final DateManager dateManager = DateFactory.createDateManager();
	private final CreateVisitPresenterView visitView;
	private SearchUserPresenter searchPatientPresenter;
	private int doctorId;
	private int patientId;
	private Date startTime;
	/**
	 * Visit that was booked for a term but not confirmed yet.
	 */
	private PatientVisit bookedVisit;
	private Set<VisitType> visitTypes;

	public CreateVisitPresenter(final CreateVisitPresenterView visitView, final EventBus eventBus) {
		super(PatientVisit.class, visitView, eventBus);
		this.visitView = visitView;
	}

	public void searchPatient() {
		if (searchPatientPresenter == null) {
			throw new NullPointerException("Search patient presenter not set");
		}
		tracer(this).debug("Searching patient");
		visitView.setViewVisible(false);
		searchPatientPresenter.getSearchUserView().setViewVisible(true);
		searchPatientPresenter.goToFirstPage();
	}

	/**
	 * Initialize presenter
	 * 
	 * @param doctor
	 * @param patient
	 * @param startTime
	 */
	public void init(final Doctor doctor, final User patient, final Date startTime) {
		init(patient.getId(), doctor.getId(), startTime);
	}

	/**
	 * Initialize presenter
	 * 
	 * @param doctorId
	 * @param patientId
	 * @param startTime
	 */
	public void init(final int doctorId, final int patientId, final Date startTime) {
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startTime = startTime;
		if (visitTypes == null) {
			tracer(this).debug("Getting visit types for doctor: " + doctorId);
			final SelectCommand<VisitType> selectCommand = new SelectCommand<VisitType>(VisitType.class);
			selectCommand.setUserId(doctorId);
			getServiceAccess().execute(selectCommand, new ResponseHandler<VisitType>() {

				@Override
				public void handle(final CommandResp<VisitType> response) {
					final SelectCommandResp<VisitType> selectCommandResp = (SelectCommandResp<VisitType>) response;
					tracer(this).debug("Visit types received for doctor: " + doctorId + ", count: " + selectCommandResp.getCount());
					visitTypes = new LinkedHashSet<VisitType>(selectCommandResp.getData());
					visitView.setVisitTypes(visitTypes);
					lockVisitTypeTerm(visitTypes.isEmpty() ? null : visitTypes.iterator().next());
				}

				@Override
				public void handleException(final Throwable throwable) {
					// ignore
				}
			});
		} else {
			lockVisitTypeTerm(visitTypes.isEmpty() ? null : visitTypes.iterator().next());
		}
	}

	/**
	 * Lock visit for specified visit type
	 * 
	 * @param visitType
	 *            visit type or null when default duration time should be used
	 */
	public void lockVisitTypeTerm(VisitType visitType) {
		tracer(this).debug("Count visit type - doctor: " + doctorId + ", patientId: " + patientId + ", startTime: " + startTime + ", visitType: " + visitType);
		Date endTime = null;
		int durationTime = 0;
		if (visitType != null) {
			durationTime = visitType.getDuration();
		} else {
			durationTime = VisitType.DEFAULT_DURATION;
		}
		endTime = new Date(startTime.getTime());
		visitView.setBeginDate(startTime);
		visitView.setDurationTime(durationTime);
		dateManager.addMinutes(endTime, durationTime);
		visitView.setEndDate(endTime);
		String visitTitle = visitType != null ? visitType.getName() : Lang.generic().patientVisit();
		lockVisitData(patientId, doctorId, startTime, endTime, visitTitle);
	}

	/**
	 * Cancel visit reservation which was made
	 */
	public void cancelBookVisit() {
		if (bookedVisit != null) {
			tracer(this).debug("Canceling booked visit: " + bookedVisit);
			visitView.setViewBlocked(true);
			final PatientVisit visitToCancel = bookedVisit;
			bookedVisit = null;
			final DeleteCommand<PatientVisit> cancelBookedVisitCommand = new DeleteCommand<PatientVisit>(PatientVisit.class, visitToCancel.getId());
			getServiceAccess().execute(cancelBookedVisitCommand, new ResponseHandler<PatientVisit>() {

				@Override
				public void handle(final CommandResp<PatientVisit> response) {
					visitView.setViewBlocked(false);
					final DeleteCommandResp<PatientVisit> cancelVisitCommandResp = (DeleteCommandResp<PatientVisit>) response;
					if (cancelVisitCommandResp.getCount() > 0) {
						visitView.setViewVisible(false);
					}
					// TODO show results
				}

				@Override
				public void handleException(final Throwable throwable) {
					visitView.setViewBlocked(false);
					bookedVisit = visitToCancel;
				}
			});
		}
	}

	/**
	 * Create element in service
	 * 
	 */
	@Override
	public void create() {
		final PatientVisit patientVisit = getEntityFromView();
		tracer(this).debug("Creating patient's visit: " + patientVisit);
		validatePresenter();
		if (patientVisit == null || !validateElement(patientVisit)) {
			tracer(this).debug("Element is not valid - create canceled");
			return;
		}
		visitView.setViewBlocked(true);
		/* visit is preliminary booked so we have to just update it */
		final UpdateCommand<PatientVisit> updateCommand = new UpdateCommand<PatientVisit>(PatientVisit.class, patientVisit);
		getServiceAccess().execute(updateCommand, new ResponseHandler<PatientVisit>() {

			@Override
			public void handle(final CommandResp<PatientVisit> response) {
				if (response instanceof UpdateCommandResp) {
					visitView.setViewBlocked(false);
					final UpdateCommandResp<PatientVisit> updateCommandResp = (UpdateCommandResp<PatientVisit>) response;
					if (updateCommandResp.getCount() > 0) {
						// broadcast change
						updateCommandResp.setUpdatedEntity(patientVisit);
						CreateCommandResp<PatientVisit> broadcastMessage = new CreateCommandResp<PatientVisit>(PatientVisit.class, patientVisit.getId());
						broadcastMessage.setCreatedEntity(patientVisit);
						getEventBus().fireEvent(new ServiceResponse<PatientVisit>(CreateVisitPresenter.this, broadcastMessage));
						bookedVisit = null;
						afterEntityCreated(patientVisit);
					}
				} else {
					// TODO: show message here
				}

			}

			@Override
			public void handleException(final Throwable throwable) {
				visitView.setViewBlocked(false);
			}
		});

	}

	/**
	 * Make reservation for a visit
	 * 
	 * @param patientId
	 * @param doctorId
	 * @param startTime
	 * @param endTime
	 * @param visitTitle
	 */
	private void lockVisitData(final int patientId, final int doctorId, final Date startTime, final Date endTime, String visitTitle) {
		tracer(this).debug("Locking visit - patientId: " + patientId + ", doctorId: " + doctorId + ", startTime: " + startTime + ", endTime: " + endTime);
		final LockVisitCommand bookVisitCommand = new LockVisitCommand();
		bookVisitCommand.setDoctorId(doctorId);
		bookVisitCommand.setPatientId(patientId);
		bookVisitCommand.setVisitStartTime(startTime);
		bookVisitCommand.setVisitEndTime(endTime);
		bookVisitCommand.setVisitTitle(visitTitle);
		if (bookedVisit != null) {
			// update of booking should be made
			bookVisitCommand.setBookedVisitId(bookedVisit.getId());
		}
		visitView.setBookingErrorVisible(false);
		visitView.setBookingOkVisible(false);
		visitView.setBookingOnGoinMsgVisible(true);
		getServiceAccess().execute(bookVisitCommand, new ResponseHandler<PatientVisit>() {

			@Override
			public void handle(final CommandResp<PatientVisit> response) {
				final BookVisitCommandResp bookingResp = (BookVisitCommandResp) response;
				visitView.setBookingOnGoinMsgVisible(false);
				visitView.setBookingOkVisible(true);
				if (bookingResp.getBookedVisit() != null) {
					bookedVisit = bookingResp.getBookedVisit();
					display(bookedVisit);
				} else {
					// TODO show that no update was made
					display(bookedVisit);
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				visitView.setBookingOnGoinMsgVisible(false);
				visitView.setBookingErrorVisible(true);
				display(bookedVisit);
			}
		});
	}

	public void setSearchPatientPresenter(final SearchUserPresenter searchPatientPresenter) {
		this.searchPatientPresenter = searchPatientPresenter;
		searchPatientPresenter.whenEntityChoosen(new Callback<User>() {

			@Override
			public void onError(Throwable t) {
				tracer(CreateVisitPresenter.this).error("Patient wasn't choosen", t);
				searchPatientPresenter.getSearchUserView().setViewVisible(false);
				visitView.setViewVisible(true);
			}

			@Override
			public void onDone(User user) {
				tracer(CreateVisitPresenter.this).debug("Patient choosen: " + user);
				patientId = user.getId();
				bookedVisit.setPatient(user);
				visitView.setPatient(user.getFirstName(), user.getLastName());
				searchPatientPresenter.getSearchUserView().setViewVisible(false);
				visitView.setViewVisible(true);
			}
		});
	}

	@Override
	protected void displayEntityOnView(final PatientVisit entity) {
		new VisitViewBinder().displayEntityOnView(visitView, getCurrentEntity());
	}

	@Override
	protected PatientVisit getEntityFromView() {
		return new VisitViewBinder().getEntityFromView(getCurrentEntity(), visitView);
	}

	@Override
	protected void clearView() {
		new VisitViewBinder().clearView(visitView);
	}

	final public CreateVisitPresenterView getVisitView() {
		return visitView;
	}

}
