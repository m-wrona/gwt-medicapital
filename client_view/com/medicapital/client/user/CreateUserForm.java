package com.medicapital.client.user;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.client.user.CreateUserPresenter;
import com.medicapital.client.user.CreateUserView;

/**
 * Class represents form for new users
 * 
 * @author michal
 * 
 */
public class CreateUserForm extends EditUserDataForm implements CreateUserView {

	private CreateUserPresenter createUserPresenter;

	public CreateUserForm() {
		login.setEnabled(true);
		passwordPanel.setVisible(false);
		buttonDelete.setVisible(false);
	}

	@Override
	protected void handleCheckLoginClick(ClickEvent event) {
		if (createUserPresenter != null) {
			createUserPresenter.checkIsLoginFree();
		}
	}

	@Override
	protected void handleSaveClick(ClickEvent event) {
		if (createUserPresenter != null) {
			createUserPresenter.create();
		}
	}

	@Override
	public void setLoginFreeMessageVisible(boolean visible) {
		validationLoginFree.setVisible(visible);
	}

	@Override
	public void setCheckLoginFreeHandlerEnabled(boolean enabled) {
		buttonCheckLogin.setEnabled(enabled);
	}

	@Override
	public void setLoginEmptyMessageVisible(boolean visible) {
		validationLogin.setVisible(visible);
	}

	@Override
	public void setLoginExistMessageVisible(boolean visible) {
		validationLoginExist.setVisible(visible);
	}

	@Override
	public HasClickHandlers getCreateClickHandler() {
		return buttonSave;
	}

	@Override
	public HasClickHandlers getCancelClickHandler() {
		return buttonClose;
	}

	@Override
	public HasClickHandlers getIsLoginFreeClickHandler() {
		return buttonCheckLogin;
	}

	@Override
	public void setEntityCreatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.confirm("Rejestracja zakończona pomyślnie");
		}
	}

	@Override
	public void setEntityNotCreatedMsgVisible(boolean visible) {
		if (visible) {
			UIUtil.error("Rejestracja zakończona błędem");
		}
	}

	@Override
	public void setCreateButtonEnabled(boolean enabled) {
		buttonSave.setEnabled(enabled);
	}

}
