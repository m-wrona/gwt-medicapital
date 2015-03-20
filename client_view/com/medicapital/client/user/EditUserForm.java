package com.medicapital.client.user;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.client.user.EditUserView;

/**
 * Form represents user profile data which can be edited
 * 
 * @author michal
 * 
 */
public class EditUserForm extends EditUserDataForm implements EditUserView {

	public EditUserForm() {
		login.setEnabled(false);
		buttonDelete.setVisible(false);
		buttonCheckLogin.setVisible(false);
	}

	@Override
	public HasClickHandlers getUpdateClickHandler() {
		return buttonSave;
	}

	@Override
	public HasClickHandlers getDeleteClickHandler() {
		return buttonDelete;
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return buttonClose;
	}

	@Override
	public void setEntityUpdatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.confirm("Zmiany zostały zapisane");
		}
	}

	@Override
	public void setEntityNotUpdatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.error("Zmiany nie zostały zapisane");
		}
	}

	@Override
	public void setEntityDeletedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.confirm("Profil został skasowany");
		}
	}

	@Override
	public void setEntityNotDeletedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.error("Profil nie został skasowany");
		}
	}

	@Override
	public void setUpdateButtonEnabled(boolean enabled) {
		buttonSave.setEnabled(enabled);
	}

	@Override
	public void setDeleteButtonEnabled(boolean enabled) {
		buttonDelete.setEnabled(enabled);
	}

}
