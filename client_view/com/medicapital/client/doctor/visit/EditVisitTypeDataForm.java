package com.medicapital.client.doctor.visit;

import static com.medicapital.client.ui.listbox.ListBoxUtils.getSelectedValue;
import static com.medicapital.client.ui.listbox.ListBoxUtils.selectValueOnListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.NumberUtils;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.date.DateFactory;

class EditVisitTypeDataForm extends PopupableComposite implements SetterVisitTypeView, GetterVisitTypeView {

	private static EditVisitTypeDataFormUiBinder uiBinder = GWT.create(EditVisitTypeDataFormUiBinder.class);

	interface EditVisitTypeDataFormUiBinder extends UiBinder<Widget, EditVisitTypeDataForm> {
	}

	@UiField
	TextBox name;
	@UiField
	ListBox duration;
	@UiField
	RichTextArea description;
	@UiField
	Button buttonAdd;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonCancel;

	public EditVisitTypeDataForm() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		setButtonCloseWindow(buttonCancel);
	}

	private void init() {
		NumberFormat timeFormat = DateFactory.getTimeFormat();
		for (int i = 5; i <= 60; i++) {
			duration.addItem(timeFormat.format(i), "" + i);
		}
		selectValueOnListBox(duration, "15");
	}

	@Override
	public String getName() {
		return name.getText();
	}

	@Override
	public int getDuration() {
		return NumberUtils.getInteger(getSelectedValue(duration));
	}

	@Override
	public String getDescription() {
		return description.getText();
	}

	@Override
	public void setName(String name) {
		this.name.setText(name);
	}

	@Override
	public void setDuration(int duration) {
		selectValueOnListBox(this.duration, "" + duration);
	}

	@Override
	public void setDescription(String description) {
		this.description.setText(description);
	}

}
