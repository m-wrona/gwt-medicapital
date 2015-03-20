package com.medicapital.client.pages.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContactPageForm extends Composite {

	interface MyUiBinder extends UiBinder<Widget, ContactPageForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public ContactPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
