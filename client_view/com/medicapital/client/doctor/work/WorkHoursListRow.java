package com.medicapital.client.doctor.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

class WorkHoursListRow extends TableRow {

	private static WorkHoursListRowUiBinder uiBinder = GWT.create(WorkHoursListRowUiBinder.class);

	interface WorkHoursListRowUiBinder extends UiBinder<Widget, WorkHoursListRow> {
	}

	@UiField 
	CheckBox selected;
	@UiField
	Label dateDay;
	@UiField
	Label workHours;
	@UiField
	Label address;
	@UiField
	Label description;
	@UiField
	PushButton buttonEdit;

	public WorkHoursListRow() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelected(selected);
	}

	public Label getDateDay() {
	    return dateDay;
    }

	public Label getWorkHours() {
		return workHours;
	}

	public Label getAddress() {
		return address;
	}

	public Label getDescription() {
		return description;
	}

	public PushButton getButtonEdit() {
		return buttonEdit;
	}

}
