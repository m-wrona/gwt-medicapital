package com.medicapital.client.pages.generic;

import com.medicapital.client.pages.Page;

final public class RegulationsPage extends Page<RegulationsPageForm> {

	@Override
	protected RegulationsPageForm createPageView() {
		return new RegulationsPageForm();
	}

	@Override
	protected void initPage(RegulationsPageForm pageView) {
		// empty
	}

}
