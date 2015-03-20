package com.medicapital.client.pages.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.DoctorBusinessForm;
import com.medicapital.client.doctor.DoctorListForm;
import com.medicapital.client.doctor.SearchDoctorCriteriaView;

public class SearchDoctorPageForm extends Composite {

	private static SearchDoctorPageFormUiBinder uiBinder = GWT.create(SearchDoctorPageFormUiBinder.class);

	interface SearchDoctorPageFormUiBinder extends UiBinder<Widget, SearchDoctorPageForm> {
	}


	@UiField
	DoctorListForm doctorList;

	private final DoctorBusinessForm doctorBusinessForm = new DoctorBusinessForm();

	public SearchDoctorPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
		doctorBusinessForm.setShowAsDialogBox(true);
	}

	public DoctorListForm getDoctorList() {
		return doctorList;
	}

	public SearchDoctorCriteriaView getSearchDoctor() {
		return doctorList.getSearchCriteria();
	}

	public DoctorBusinessForm getDoctorsDetailsView() {
		return doctorBusinessForm;
	}

}
