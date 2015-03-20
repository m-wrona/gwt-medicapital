package com.medicapital.client.user;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;

final public class SearchUserForm extends DataTable<SearchUserFormHeader, SearchUserFormRow, User> implements SearchUserView {

	@Override
	protected SearchUserFormHeader createHeader() {
		return new SearchUserFormHeader();
	}

	@Override
	public void display(final int userId, String firstName, String lastName, UserRole userRole) {
		SearchUserFormRow row = new SearchUserFormRow();
		row.getFirstName().setText(firstName);
		row.getLastName().setText(lastName);
		row.getUserRole().setText(userRole.toString());
		addRow(userId, row);
	}

	@Override
	public String getSearchFirstName() {
		return getHeader().getSearchFirstName().getText();
	}

	@Override
	public String getSearchLastName() {
		return getHeader().getSearchLastName().getText();
	}

	@Override
	public HasClickHandlers getSearchClickHandler() {
		return getHeader().getButtonSearch();
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return getHeader().getButtonCancel();
	}

	@Override
	public HasClickHandlers getDisplayDetailClickHandler(int entityId) {
		return getRowIdRowMap().get(entityId).getButtonSelect();
	}

}
