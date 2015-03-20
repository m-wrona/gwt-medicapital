package com.medicapital.client.pages.generic;

import com.medicapital.client.pages.Page;

final public class OfferPage extends Page<OfferPageForm> {

	@Override
	protected OfferPageForm createPageView() {
		return new OfferPageForm();
	}

	@Override
	protected void initPage(OfferPageForm pageView) {
		// empty
	}

}
