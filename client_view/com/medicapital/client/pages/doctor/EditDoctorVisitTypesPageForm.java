package com.medicapital.client.pages.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.visit.CreateVisitTypeForm;
import com.medicapital.client.doctor.visit.CreateVisitTypeView;
import com.medicapital.client.doctor.visit.EditVisitTypeForm;
import com.medicapital.client.doctor.visit.EditVisitTypeListForm;
import com.medicapital.client.doctor.visit.EditVisitTypeView;

public class EditDoctorVisitTypesPageForm extends Composite {

	private static EditDoctorVisitTypesPageFormUiBinder uiBinder = GWT.create(EditDoctorVisitTypesPageFormUiBinder.class);

	interface EditDoctorVisitTypesPageFormUiBinder extends UiBinder<Widget, EditDoctorVisitTypesPageForm> {
	}

	@UiField
	EditVisitTypeListForm visitTypes;

	public EditDoctorVisitTypesPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public EditVisitTypeListForm getVisitTypes() {
		return visitTypes;
	}

	public CreateVisitTypeView getCreateVisitTypeView() {
		CreateVisitTypeForm createVisitTypeForm = new CreateVisitTypeForm();
		createVisitTypeForm.setShowAsDialogBox(true);
		return createVisitTypeForm;
	}

	public EditVisitTypeView getEditVisitTypeView() {
		EditVisitTypeForm editVisitTypeForm = new EditVisitTypeForm();
		editVisitTypeForm.setShowAsDialogBox(true);
		return editVisitTypeForm;
	}

}
