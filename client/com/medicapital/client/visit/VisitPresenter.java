package com.medicapital.client.visit;

import static com.medicapital.client.log.Tracer.tracer;

import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.client.dao.CommandRespBroadcastHandler;
import com.medicapital.client.evaluation.CreateVisitEvaluationPresenter;
import com.medicapital.client.evaluation.VisitEvaluationPresenter;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;

public class VisitPresenter extends EntityPresenter<PatientVisit> {

	private final VisitPresenterView display;
	private VisitEvaluationPresenter evaluationPresenter;
	private CreateVisitEvaluationPresenter createEvaluationPresenter;
	private boolean canAddEvaluations = true;

	public VisitPresenter(final VisitPresenterView display, final EventBus eventBus) {
		super(PatientVisit.class, display, eventBus);
		this.display = display;
		display.setDisplayEvaluationVisible(false);
		display.setCreateEvaluationVisible(false);
	}

	@Override
	protected void registerEventBusHandlers(EventBus eventBus, RegistrationList registrationList) {
		super.registerEventBusHandlers(eventBus, registrationList);
		getRegistrationList().add(eventBus.addHandler(ClientEvent.TYPE, new CommandRespBroadcastHandler<PatientEvaluation>(this) {

			@Override
			protected void handleCreatedEntity(PatientEvaluation createdEntity) {
				if (getCurrentEntity() != null && getCurrentEntity().getId() == createdEntity.getVisitId()) {
					getCurrentEntity().setEvaluation(createdEntity);
					refreshDisplay(false);
				}
			}

			@Override
			protected void handleUpdatedEntity(PatientEvaluation updatedEntity) {
				if (getCurrentEntity() != null && getCurrentEntity().getId() == updatedEntity.getVisitId()) {
					getCurrentEntity().setEvaluation(updatedEntity);
					refreshDisplay(false);
				}
			}

			@Override
			protected void handleDeletedEntities(Set<Integer> deletedEntitiesIds) {
				if (getCurrentEntity() != null && getCurrentEntity().getEvaluation() != null && deletedEntitiesIds.contains(getCurrentEntity().getEvaluation().getId())) {
					getCurrentEntity().setEvaluation(null);
					refreshDisplay(false);
				}
			}

			@Override
			protected Class<PatientEvaluation> getEntityClass() {
				return PatientEvaluation.class;
			}

		}));
	}

	/**
	 * Show evaluation
	 * 
	 * @throws IllegalArgumentException
	 */
	final public void displayEvaluation() throws IllegalArgumentException {
		if (getCurrentEntity() == null) {
			throw new IllegalArgumentException("No visit is displayed");
		}
		tracer(this).debug("Showing evaluation for visit: " + getCurrentEntity().getId());
		if (evaluationPresenter == null) {
			throw new IllegalArgumentException("Evaluation presenter not set");
		} else if (getCurrentEntity().getEvaluation() == null) {
			throw new IllegalArgumentException("Current visit has no evaluation to display");
		}
		evaluationPresenter.display(getCurrentEntity().getEvaluation());
	}

	/**
	 * Create evaluation of current visit
	 * 
	 * @throws IllegalArgumentException
	 */
	final public void createEvaluation() throws IllegalArgumentException {
		if (getCurrentEntity() == null) {
			throw new IllegalArgumentException("No visit is displayed");
		}
		tracer(this).debug("Creating evaluation for visit: " + getCurrentEntity().getId());
		if (!canAddEvaluations) {
			throw new IllegalArgumentException("Evaluation can't be added using presenter");
		} else if (createEvaluationPresenter == null) {
			throw new IllegalArgumentException("Create evaluation presenter not set");
		}
		final PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setVisitId(getCurrentEntity().getId());
		createEvaluationPresenter.display(evaluation);
	}

	@Override
	protected void displayEntityOnView(final PatientVisit entity) {
		new VisitViewBinder().displayEntityOnView(display, entity);
		display.setDisplayEvaluationVisible(false);
		display.setCreateEvaluationVisible(false);
		if (entity.getEvaluation() != null) {
			display.setDisplayEvaluationVisible(true);
		} else {
			display.setCreateEvaluationVisible(true);
		}
	}

	@Override
	protected void clearView() {
		new VisitViewBinder().clearView(display);
	}

	final public void setEvaluationPresenter(final VisitEvaluationPresenter evaluationPresenter) {
		this.evaluationPresenter = evaluationPresenter;
	}

	final public VisitEvaluationPresenter getEvaluationPresenter() {
		return evaluationPresenter;
	}

	final public void setCreateEvaluationPresenter(final CreateVisitEvaluationPresenter createEvaluationPresenter) {
		this.createEvaluationPresenter = createEvaluationPresenter;
	}

	final public CreateVisitEvaluationPresenter getCreateEvaluationPresenter() {
		return createEvaluationPresenter;
	}

	final public void setCanAddEvaluations(boolean canAddEvaluations) {
		this.canAddEvaluations = canAddEvaluations;
		if (display != null) {
			display.setEvaluationButtonVisible(canAddEvaluations);
		}
	}

	final public boolean isCanAddEvaluations() {
		return canAddEvaluations;
	}

}
