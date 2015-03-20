package com.medicapital.client.doctor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.doctor.EditDoctorView;
import com.medicapital.client.ui.UIUtil;

public class EditDoctorForm extends EditDoctorDataForm implements EditDoctorView {

	public EditDoctorForm() {
		userUI.getButtonCheckLogin().setVisible(false);
		userUI.getButtonSave().setVisible(true);
		userUI.getButtonClose().setVisible(true);
	}

	@Override
	public HasClickHandlers getUpdateClickHandler() {
		return userUI.getButtonSave();
	}

	@Override
	public HasClickHandlers getDeleteClickHandler() {
		return userUI.getButtonDelete();
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return userUI.getButtonClose();
	}

	@Override
	public void setEntityUpdatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.confirm("Profil został zaktualizowany");
		}
	}

	@Override
	public void setEntityNotUpdatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.error("Profil nie został zaktualizowany");
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
		userUI.getButtonSave().setEnabled(enabled);
	}

	@Override
	public void setDeleteButtonEnabled(boolean enabled) {
		userUI.getButtonDelete().setEnabled(enabled);
	}

}
