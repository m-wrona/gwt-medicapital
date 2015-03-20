package com.medicapital.client.doc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MedicalHistoryForm extends Composite {

	private static MedicalHistoryFormUiBinder uiBinder = GWT.create(MedicalHistoryFormUiBinder.class);

	interface MedicalHistoryFormUiBinder extends UiBinder<Widget, MedicalHistoryForm> {
	}

	public MedicalHistoryForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
