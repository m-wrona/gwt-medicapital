package com.medicapital.client.doctor.visit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

public class EditVisitTypeListRow extends TableRow {

	private static EditVisitTypeListRowUiBinder uiBinder = GWT.create(EditVisitTypeListRowUiBinder.class);

	interface EditVisitTypeListRowUiBinder extends UiBinder<Widget, EditVisitTypeListRow> {
	}

	@UiField 
	CheckBox selected;
	@UiField
	Label visitType;
	@UiField
	Label duration;
	@UiField
	Label description;
	@UiField
	PushButton buttonEdit;

	public EditVisitTypeListRow() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelected(selected);
	}

	public Label getVisitType() {
		return visitType;
	}

	public Label getDuration() {
		return duration;
	}

	public Label getDescription() {
		return description;
	}

	public PushButton getButtonEdit() {
		return buttonEdit;
	}

}
