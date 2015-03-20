package com.medicapital.client.visit;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Collection;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.calendar.access.CalendarEventAccessController;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.core.entities.EditableEntitiesPresenter;
import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.VisitEvaluationPresenter;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;

/**
 * Class allows to display visits in list form. From list also evaluations can
 * be displayed and created directly.
 * 
 * @author michal
 * 
 */
public class EditablePatientVisitListPresenter extends EditableEntitiesPresenter<PatientVisit> {

	private final EditablePatientVisitListView view;
	private VisitPresenter visitPresenter;
	private EditVisitPresenter editVisitPresenter;
	private VisitEvaluationPresenter evaluationPresenter;
	private CreateVisitEvaluationPresenter createEvaluationPresenter;
	private boolean canAddEvaluations = true;

	public EditablePatientVisitListPresenter(final EditablePatientVisitListView display, final EventBus eventBus) {
		super(PatientVisit.class, display, eventBus);
		view = display;
	}

	@Override
	final protected void displayDataOnView(final Collection<PatientVisit> data) {
		for (final PatientVisit patientVisit : data) {
			final String address = patientVisit.getLocalization().getAddress();
			final String doctorName = patientVisit.getDoctor().getUser().getFirstName() + " " + patientVisit.getDoctor().getUser().getLastName();
			final boolean isVisitEditable = CalendarEventAccessController.isEventEditable(patientVisit);
			final boolean hasEvaluation = patientVisit.getEvaluation() != null;
			tracer(this).debug("Visit: " + patientVisit.getId() + ", isEditable: " + isVisitEditable + ", hasEvaluation: " + hasEvaluation);
			if (isVisitEditable) {
				view.addPlannedVisit(patientVisit.getId(), patientVisit.getStartTime(), address, doctorName);
			} else {
				view.addVisit(patientVisit.getId(), patientVisit.getStartTime(), address, doctorName, hasEvaluation);
			}
		}
	}

	/**
	 * Show details of the visit
	 * 
	 * @param visitId
	 * @throws IllegalArgumentException
	 */
	final public void displayVisitDetails(final int visitId) throws IllegalArgumentException {
		tracer(this).debug("Showing details of visit: " + visitId);
		final PatientVisit visit = getVisit(visitId);
		final boolean isVisitEditable = CalendarEventAccessController.isEventEditable(visit);
		tracer(this).debug("Visit is editable: " + isVisitEditable);
		if (isVisitEditable) {
			if (editVisitPresenter == null) {
				throw new IllegalArgumentException("Edit visit presenter not set");
			}
			editVisitPresenter.display(visit);
		} else {
			if (visitPresenter == null) {
				throw new IllegalArgumentException("Visit presenter not set");
			}
			visitPresenter.display(visit);
		}

	}

	/**
	 * Show evaluation of the visit
	 * 
	 * @param visitId
	 * @throws IllegalArgumentException
	 *             when evaluation presenter is not set or visit doesn't belong
	 *             to list
	 */
	final public void displayVisitEvaluation(final int visitId) throws IllegalArgumentException {
		tracer(this).debug("Showing evaluation of visit: " + visitId);
		if (evaluationPresenter == null) {
			throw new IllegalArgumentException("Evaluation presenter not set");
		}
		final PatientVisit visit = getVisit(visitId);
		tracer(this).debug("Evaluation set: " + (visit.getEvaluation() != null));
		if (visit.getEvaluation() != null) {
			evaluationPresenter.display(visit.getEvaluation());
		}
	}

	/**
	 * Add evaluation for the visit
	 * 
	 * @param visitId
	 * @throws IllegalArgumentException
	 *             when visit doesn't belong to list or when create evaluation
	 *             presenter is not set.
	 */
	final public void addVisitEvaluation(final int visitId) throws IllegalArgumentException {
		tracer(this).debug("Adding evaluation to the visit: " + visitId);
		if (!canAddEvaluations) {
			throw new IllegalArgumentException("Evaluation can't be added to the visit list presenter");
		} else if (createEvaluationPresenter == null) {
			throw new IllegalArgumentException("Create evaluation presenter not set");
		}
		final PatientVisit visit = getVisit(visitId);
		tracer(this).debug("Evaluation set: " + (visit.getEvaluation() != null));
		if (visit.getEvaluation() == null) {
			final PatientEvaluation evaluation = new PatientEvaluation();
			evaluation.setVisitId(visit.getId());
			createEvaluationPresenter.display(evaluation);
		}
	}

	private PatientVisit getVisit(final int visitId) {
		final PatientVisit visit = getDisplayedElements().get(visitId);
		if (visit == null) {
			throw new IllegalArgumentException("Visit " + visitId + " is not displayed on list");
		}
		return visit;
	}

	@Override
	protected void registerEventBusHandlers(final EventBus eventBus, final RegistrationList registrationList) {
		super.registerEventBusHandlers(eventBus, registrationList);
		getListHandlers().add(eventBus.addHandler(ClientEvent.TYPE, new CommandRespBroadcastHandler<PatientEvaluation>(this) {

			@Override
			protected void handleCreatedEntity(final PatientEvaluation createdEntity) {
				EditablePatientVisitListPresenter.this.handleCreatedEvaluation(createdEntity);
			}

			@Override
			protected void handleDeletedEntities(final Set<Integer> deletedEntitiesIds) {
				EditablePatientVisitListPresenter.this.handleDeletedEvaluations(deletedEntitiesIds);
			}

			@Override
			protected void handleUpdatedEntity(final PatientEvaluation updatedEntity) {
				EditablePatientVisitListPresenter.this.handleUpdatedEvaluation(updatedEntity);
			}

			@Override
			protected Class<PatientEvaluation> getEntityClass() {
				return PatientEvaluation.class;
			}
		}));
	}

	/**
	 * Handle updated evaluation
	 * 
	 * @param updatedEntity
	 */
	private void handleUpdatedEvaluation(final PatientEvaluation updatedEntity) {
		for (final PatientVisit visit : getDisplayedElements().values()) {
			if (visit.getEvaluation() != null && visit.getEvaluation().getId() == updatedEntity.getId()) {
				tracer(this).debug("Evaluation of visit " + visit.getId() + " was updated: " + updatedEntity);
				visit.setEvaluation(updatedEntity);
				refreshDisplay(false);
				return;
			}
		}
	}

	/**
	 * Handle created evaluation
	 * 
	 * @param createdEntity
	 */
	private void handleCreatedEvaluation(final PatientEvaluation createdEntity) {
		for (final PatientVisit visit : getDisplayedElements().values()) {
			if (visit.getId() == createdEntity.getVisitId()) {
				tracer(this).debug("Evaluation created for visit " + visit.getId() + " - evaluation: " + createdEntity);
				visit.setEvaluation(createdEntity);
				refreshDisplay(false);
				return;
			}
		}
	}

	/**
	 * Handle deleted evaluations
	 * 
	 * @param deletedEntitiesIds
	 */
	private void handleDeletedEvaluations(final Set<Integer> deletedEntitiesIds) {
		boolean evaluationsDeleted = false;
		for (final PatientVisit visit : getDisplayedElements().values()) {
			if (visit.getEvaluation() != null && deletedEntitiesIds.contains(visit.getEvaluation().getId())) {
				tracer(this).debug("Evaluation of visit " + visit.getId() + " was deleted - evaluation id: " + visit.getEvaluation().getId());
				visit.setEvaluation(null);
				evaluationsDeleted = true;
			}
		}
		if (evaluationsDeleted) {
			refreshDisplay(false);
		}
	}

	@Override
	public void clearPresenter() {
		super.clearPresenter();
		if (visitPresenter != null) {
			visitPresenter.clearPresenter();
			visitPresenter = null;
		}
		if (editVisitPresenter != null) {
			editVisitPresenter.clearPresenter();
			editVisitPresenter = null;
		}
		if (evaluationPresenter != null) {
			evaluationPresenter.clearPresenter();
			evaluationPresenter = null;
		}
		if (createEvaluationPresenter != null) {
			createEvaluationPresenter.clearPresenter();
			createEvaluationPresenter = null;
		}
	}

	final public void setCreateEvaluationPresenter(final CreateVisitEvaluationPresenter createEvaluationPresenter) {
		this.createEvaluationPresenter = createEvaluationPresenter;
	}

	final public void setEvaluationPresenter(final VisitEvaluationPresenter displayEvaluationPresenter) {
		evaluationPresenter = displayEvaluationPresenter;
	}

	final public CreateVisitEvaluationPresenter getCreateEvaluationPresenter() {
		return createEvaluationPresenter;
	}

	final public VisitEvaluationPresenter getEvaluationPresenter() {
		return evaluationPresenter;
	}

	final public void setEditVisitPresenter(final EditVisitPresenter editVisitPresenter) {
		this.editVisitPresenter = editVisitPresenter;
	}

	final public EditVisitPresenter getEditVisitPresenter() {
		return editVisitPresenter;
	}

	final public void setVisitPresenter(final VisitPresenter visitPresenter) {
		this.visitPresenter = visitPresenter;
	}

	final public VisitPresenter getVisitPresenter() {
		return visitPresenter;
	}

	final public boolean isCanAddEvaluations() {
		return canAddEvaluations;
	}

	final public void setCanAddEvaluations(boolean canAddEvaluations) {
		this.canAddEvaluations = canAddEvaluations;
		if (view != null) {
			view.setEvaluationButtonVisible(canAddEvaluations);
		}
		if (visitPresenter != null) {
			visitPresenter.setCanAddEvaluations(canAddEvaluations);
		}
	}

}
