package com.medicapital.client.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

public class DoctorHeaderForm extends TableHeader {

	private static DoctorHeaderFormUiBinder uiBinder = GWT.create(DoctorHeaderFormUiBinder.class);

	interface DoctorHeaderFormUiBinder extends UiBinder<Widget, DoctorHeaderForm> {
	}

	@UiField
	SearchDoctorForm searchForm;

	public DoctorHeaderForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SearchDoctorForm getSearchForm() {
		return searchForm;
	}

}
