package com.medicapital.client.visit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.calendar.CalendarEventView;
import com.medicapital.client.visit.EditVisitPresenter;
import com.medicapital.client.visit.EditVisitView;
import com.medicapital.common.entities.PatientVisit;

/**
 * Class represents visit details form
 * 
 * @author michal
 * 
 */
public class EditVisitForm extends EditVisitDataForm implements EditVisitView, CalendarEventView<PatientVisit> {

	private EditVisitPresenter editVisitPresenter;

	public EditVisitForm() {
		setButtonCloseWindow(getButtonCancel());
		initForm();
		initClickHandlers();
	}

	protected void initForm() {
		address.setEnabled(false);
		title.setEnabled(false);
		cities.setEnabled(false);
		regions.setEnabled(false);
		// TODO remove it
		// labelEventType.setVisible(false);
		// eventType.setVisible(false);
		// labelDuration.setVisible(false);
		// durationTime.setVisible(false);
	}

	private void initClickHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editVisitPresenter != null) {
					editVisitPresenter.update();
				}
			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editVisitPresenter != null) {
					editVisitPresenter.delete();
				}
			}
		});
		doctor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editVisitPresenter != null) {
					editVisitPresenter.displayDoctorDetails();
				}
			}
		});
	}

	@Override
	public void setDeleteHandlerEnabled(boolean enabled) {
		buttonDelete.setEnabled(enabled);
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		getButtonAdd().setEnabled(enabled);
	}

	@Override
	public void setEditVisitPresenter(EditVisitPresenter editVisitPresenter) {
		this.editVisitPresenter = editVisitPresenter;
	}

}
