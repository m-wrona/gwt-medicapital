package com.medicapital.client.pages.generic;

import com.medicapital.client.pages.Page;

final public class ContactPage extends Page<ContactPageForm> {

	@Override
	protected ContactPageForm createPageView() {
		return new ContactPageForm();
	}

	@Override
	protected void initPage(ContactPageForm pageView) {
		// empty
	}

}
