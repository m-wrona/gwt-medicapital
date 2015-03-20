package com.medicapital.client.pages.generic;

import com.medicapital.client.pages.Page;

final public class HelpPage extends Page<HelpPageForm> {

	@Override
	protected HelpPageForm createPageView() {
		return new HelpPageForm();
	}

	@Override
	protected void initPage(HelpPageForm pageView) {
		// empty
	}

}
