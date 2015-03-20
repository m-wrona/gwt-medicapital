package com.medicapital.client.login;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;

public class LoggedUserForm extends Composite implements LoggedUserView {

	interface MyUiBinder extends UiBinder<Widget, LoggedUserForm> {
	}

	static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private LoginPresenter loginPresenter;
	@UiField
	Label login;
	@UiField
	Button buttonLoggedOut;
	@UiField
	Label lastLoginDate;
	@UiField
	Label lastLoginHour;

	public LoggedUserForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("buttonLoggedOut")
	public void logout(ClickEvent event) {
		if (loginPresenter != null) {
			loginPresenter.logout();
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public void setLoginPresenter(LoginPresenter loginPresenter) {
		this.loginPresenter = loginPresenter;
	}

	public LoginPresenter getLoginPresenter() {
		return loginPresenter;
	}

	@Override
	public void setLogin(String text) {
		login.setText(text);
	}

	@Override
	public void setLastLoginDate(Date date) {
		lastLoginDate.setText(dateFormatter.toDateString(date));
		lastLoginHour.setText(dateFormatter.toHourMinuteString(date));
	}

	@Override
	public void setLogoutButtonEnabled(boolean enabled) {
		buttonLoggedOut.setEnabled(enabled);
	}

}
