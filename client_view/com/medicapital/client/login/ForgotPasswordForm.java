package com.medicapital.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.login.ForgotPasswordView;
import com.medicapital.client.ui.UIUtil;

public class ForgotPasswordForm extends Composite implements ForgotPasswordView {

	interface MyUiBinder extends UiBinder<Widget, ForgotPasswordForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private ForgotPasswordPresenter forgotPasswordPresenter;
	@UiField
	TextBox login;
	@UiField
	TextBox eMail;
	@UiField
	Button buttonSendEmail;
	@UiField
	Button buttonClose;
	@UiField
	HTMLPanel validationWrongLoginEmail;
	@UiField
	HTMLPanel validationNoLoginEmail;
	@UiField
	HTMLPanel msgEMailSent;
	@UiField
	HTMLPanel msgServerError;

	private DialogBox dialogBox = new DialogBox();

	public ForgotPasswordForm() {
		initWidget(uiBinder.createAndBindUi(this));
		setStyleName("popupWindow");
		dialogBox.setStyleName("popupWindow");
		dialogBox.add(this);
		dialogBox.setModal(true);
		dialogBox.setAutoHideEnabled(false);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			setLoginEmailNotFoundMsgVisible(false);
			setLoginOrEmailNotValidMsgVisible(false);
			setEMailSentMsgVisible(false);
			setServerErrorMsgVisible(false);
			dialogBox.show();
			login.setText("");
			eMail.setText("");
		} else {
			dialogBox.hide();
		}
	}

	@UiHandler("buttonClose")
	public void closeWindow(ClickEvent event) {
		setVisible(false);
	}

	@UiHandler("buttonSendEmail")
	public void sendForgottenEMail(ClickEvent event) {
		if (forgotPasswordPresenter != null) {
			forgotPasswordPresenter.sendForgotPasswordEmail();
		}
	}

	@Override
	public void setSendPasswordHandlerEnabled(boolean enabled) {
		buttonSendEmail.setEnabled(enabled);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setEMailSentMsgVisible(boolean visible) {
		msgEMailSent.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(msgEMailSent);
		}
	}

	@Override
	public void setLoginEmailNotFoundMsgVisible(boolean visible) {
		validationNoLoginEmail.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(validationNoLoginEmail);
		}
	}

	@Override
	public void setLoginOrEmailNotValidMsgVisible(boolean visible) {
		validationWrongLoginEmail.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(validationWrongLoginEmail);
		}
	}

	@Override
	public void setServerErrorMsgVisible(boolean visible) {
		msgServerError.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(msgServerError);
		}
	}

	public void setForgotPasswordPresenter(ForgotPasswordPresenter forgotPasswordPresenter) {
		this.forgotPasswordPresenter = forgotPasswordPresenter;
	}

	public ForgotPasswordPresenter getForgotPasswordPresenter() {
		return forgotPasswordPresenter;
	}

	@Override
	public String getEmail() {
		return eMail.getText();
	}

	@Override
	public String getLogin() {
		return login.getText();
	}

}
