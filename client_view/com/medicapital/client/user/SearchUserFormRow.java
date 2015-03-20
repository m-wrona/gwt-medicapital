package com.medicapital.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

class SearchUserFormRow extends TableRow {

	private static UserListFormRowUiBinder uiBinder = GWT.create(UserListFormRowUiBinder.class);

	interface UserListFormRowUiBinder extends UiBinder<Widget, SearchUserFormRow> {
	}

	@UiField
	Anchor lastName;
	@UiField
	Anchor firstName;
	@UiField
	Label userRole;
	@UiField
	PushButton buttonSelect;
	
	public SearchUserFormRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Anchor getLastName() {
		return lastName;
	}

	public Anchor getFirstName() {
		return firstName;
	}

	public Label getUserRole() {
		return userRole;
	}

	public PushButton getButtonSelect() {
		return buttonSelect;
	}

}
