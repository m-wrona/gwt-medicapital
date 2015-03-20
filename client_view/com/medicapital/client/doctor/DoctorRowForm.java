package com.medicapital.client.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

public class DoctorRowForm extends TableRow {

	private static DoctorRowFormUiBinder uiBinder = GWT.create(DoctorRowFormUiBinder.class);

	interface DoctorRowFormUiBinder extends UiBinder<Widget, DoctorRowForm> {
	}

	@UiField
	Label firstName;
	@UiField
	Label lastName;
	@UiField
	Label evaluation;
	@UiField
	PushButton buttonDetails;

	public DoctorRowForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Label getFirstName() {
		return firstName;
	}

	public void setFirstName(Label firstName) {
		this.firstName = firstName;
	}

	public Label getLastName() {
		return lastName;
	}

	public void setLastName(Label lastName) {
		this.lastName = lastName;
	}

	public Label getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Label evaluation) {
		this.evaluation = evaluation;
	}
	
	public PushButton getButtonDetails() {
	    return buttonDetails;
    }

}
