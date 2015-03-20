package com.medicapital.client.visit;

import java.util.Date;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.client.visit.EditablePatientVisitListPresenter;
import com.medicapital.client.visit.EditablePatientVisitListView;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.PatientVisit;

/**
 * Component displays patient's visit in table form
 * 
 * @author michal
 * 
 */
public class VisitListForm extends DataTable<VisitListFormHeader, VisitListFormRow, PatientVisit> implements EditablePatientVisitListView {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private EditablePatientVisitListPresenter presenter;
	private boolean evaluationButtonVisible = true;

	public VisitListForm() {
		setDeleteSelectedButtonVisible(true);
		setSelectAllEnabled(true);
	}

	@Override
	protected VisitListFormHeader createHeader() {
		return new VisitListFormHeader();
	}

	@Override
	public void addPlannedVisit(final int visitId, Date visitDate, String visitTitle, String doctorName) {
		VisitListFormRow row = new VisitListFormRow();
		row.setRowId(visitId);
		row.getCreated().setText(dateFormatter.toDateString(visitDate));
		row.getVisitTitle().setText(visitTitle);
		row.getDoctorName().setText(doctorName);
		getHandlers().add(row.getShowDetails().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (presenter != null) {
					presenter.displayVisitDetails(visitId);
				}
			}
		}));
		addRow(row);
	}

	@Override
	public void addVisit(final int visitId, Date visitDate, String visitTitle, String doctorName, boolean evaluationAdded) {
		VisitListFormRow row = new VisitListFormRow();
		row.setRowId(visitId);
		row.setEditable(false);
		row.getCreated().setText(dateFormatter.toDateString(visitDate));
		row.getVisitTitle().setText(visitTitle);
		row.getDoctorName().setText(doctorName);
		getHandlers().add(row.getShowDetails().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (presenter != null) {
					presenter.displayVisitDetails(visitId);
				}
			}
		}));
		addRow(row);
		if (evaluationAdded) {
			getHandlers().add(row.getShowEvaluation().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (presenter != null) {
						presenter.displayVisitEvaluation(visitId);
					}

				}
			}));
			row.getShowEvaluation().setVisible(true);
		} else if (evaluationButtonVisible) {
			getHandlers().add(row.getAddEvaluation().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (presenter != null) {
						presenter.addVisitEvaluation(visitId);
					}

				}
			}));
			row.getAddEvaluation().setVisible(true);
		}
		addRow(row);
	}

	@Override
	public void setEvaluationButtonVisible(boolean visible) {
		evaluationButtonVisible = visible;
	}

	@Override
	public void setPresenter(EditablePatientVisitListPresenter presenter) {
		this.presenter = presenter;
		setEditableEntitiesPresenter(presenter);
	}

}
