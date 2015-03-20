package com.medicapital.client.visit;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.user.client.Window;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.client.core.entity.EditEntityPresenter;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.entities.PatientVisit;
import com.google.gwt.event.shared.EventBus;

/**
 * Presenter manages displaying and editing details of pacient's visit
 * 
 * @author michal
 * 
 */
public class EditVisitPresenter extends EditEntityPresenter<PatientVisit> {

	private final EditVisitView visitView;
	private EntityPresenter<PatientEvaluation> displayEvaluationPresenter;
	private CreateEntityPresenter<PatientEvaluation> createEvaluationPresenter;

	public EditVisitPresenter(final EditVisitView display, final EventBus eventBus) {
		super(PatientVisit.class, display, eventBus);
		this.visitView = display;
	}

	@Override
	protected void displayEntityOnView(PatientVisit entity) {
		new VisitViewBinder().displayEntityOnView(visitView, entity);
	}

	@Override
	protected PatientVisit getEntityFromView() {
		PatientVisit visit = new VisitViewBinder().getEntityFromView(getCurrentEntity(), visitView);
		visit.setEndTime(getCurrentEntity().getEndTime());
		return visit;
	}

	@Override
	protected void clearView() {
		new VisitViewBinder().clearView(visitView);
	}

	/**
	 * Show details about doctor
	 */
	final public void displayDoctorDetails() {
		tracer(this).debug("Displaying doctor details");
		// TODO
		Window.alert("Not implemented yet");
	}

	public void setCreateEvaluationPresenter(CreateEntityPresenter<PatientEvaluation> createEvaluationPresenter) {
		this.createEvaluationPresenter = createEvaluationPresenter;
	}

	public void setDisplayEvaluationPresenter(EntityPresenter<PatientEvaluation> displayEvaluationPresenter) {
		this.displayEvaluationPresenter = displayEvaluationPresenter;
	}

	public CreateEntityPresenter<PatientEvaluation> getCreateEvaluationPresenter() {
		return createEvaluationPresenter;
	}

	public EntityPresenter<PatientEvaluation> getDisplayEvaluationPresenter() {
		return displayEvaluationPresenter;
	}

	final public EditVisitView getVisitView() {
		return visitView;
	}

}
