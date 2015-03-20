package com.medicapital.client.pages.user;

import com.medicapital.client.pages.UserPage;

public class UserHomePage extends UserPage<UserHomePageForm> {

	@Override
	protected UserHomePageForm createPageView() {
		return new UserHomePageForm();
	}

	@Override
	protected void initPage(UserHomePageForm pageView) {
		// empty
	}

}
