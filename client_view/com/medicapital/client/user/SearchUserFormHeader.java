package com.medicapital.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

class SearchUserFormHeader extends TableHeader {

	private static UserListFormHeaderUiBinder uiBinder = GWT.create(UserListFormHeaderUiBinder.class);

	interface UserListFormHeaderUiBinder extends UiBinder<Widget, SearchUserFormHeader> {
	}

	@UiField
	TextBox searchFirstName;
	@UiField
	TextBox searchLastName;
	@UiField
	PushButton buttonSearch;
	@UiField
	PushButton buttonCancel;

	public SearchUserFormHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TextBox getSearchFirstName() {
		return searchFirstName;
	}

	public TextBox getSearchLastName() {
		return searchLastName;
	}

	public PushButton getButtonSearch() {
		return buttonSearch;
	}

	public PushButton getButtonCancel() {
		return buttonCancel;
	}

}
