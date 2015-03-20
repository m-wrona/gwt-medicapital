package com.medicapital.client.pages.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserHomePageForm extends Composite {

	private static UserHomePageFormUiBinder uiBinder = GWT.create(UserHomePageFormUiBinder.class);

	interface UserHomePageFormUiBinder extends UiBinder<Widget, UserHomePageForm> {
	}

	public UserHomePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
