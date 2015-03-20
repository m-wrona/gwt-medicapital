package com.medicapital.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.login.LoginView;
import com.medicapital.client.ui.UIUtil;

/**
 * Class displays login user view
 * 
 * @author michal
 * 
 */
public class LoginForm extends Composite implements LoginView {

	interface MyUiBinder extends UiBinder<Widget, LoginForm> {
	}

	static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private ForgotPasswordView forgotPasswordView;
	private LoginPresenter loginPresenter;
	@UiField
	TextBox login;
	@UiField
	PasswordTextBox password;
	@UiField
	PushButton buttonLogin;
	@UiField
	PushButton buttonForgotPassword;
	@UiField
	PushButton buttonRegister;
	@UiField
	HTMLPanel validationWrongLoginData;
	@UiField
	HTMLPanel validationEmptyData;

	public LoginForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("buttonLogin")
	public void login(ClickEvent event) {
		if (loginPresenter != null) {
			loginPresenter.login();
		}
	}

	@UiHandler("buttonRegister")
	public void register(ClickEvent event) {
		if (loginPresenter != null) {
			loginPresenter.registerNewUser();
		}
	}

	@UiHandler("buttonForgotPassword")
	public void sendForgottenPassword(ClickEvent event) {
		if (forgotPasswordView != null) {
			forgotPasswordView.setVisible(true);
		}
	}

	@Override
	public void setWrongLoginDataMessageVisible(boolean visible) {
		validationWrongLoginData.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(validationWrongLoginData);
		}
	}

	@Override
	public void setNoLoginPasswordMessageVisible(boolean visible) {
		validationEmptyData.setVisible(visible);
		if (visible) {
			UIUtil.autoHideMessage(validationEmptyData);
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setLoginHandlerEnabled(boolean enabled) {
		buttonLogin.setEnabled(enabled);
	}

	@Override
	public void setRegisterHandlerEnabled(boolean enabled) {
		buttonRegister.setEnabled(enabled);
	}

	@Override
	public void setForgotPasswordHandlerEnabled(boolean enabled) {
		buttonForgotPassword.setEnabled(enabled);
	}

	public void setLoginPresenter(LoginPresenter loginPresenter) {
		this.loginPresenter = loginPresenter;
	}

	public LoginPresenter getLoginPresenter() {
		return loginPresenter;
	}

	public void setForgotPasswordView(ForgotPasswordView forgotPasswordView) {
		this.forgotPasswordView = forgotPasswordView;
	}

	public ForgotPasswordView getForgotPasswordView() {
		return forgotPasswordView;
	}

	@Override
	public void setLogin(String text) {
		login.setText(text);
	}

	@Override
	public String getLogin() {
		return login.getText();
	}

	@Override
	public void setPassword(String text) {
		password.setText(text);
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

}
