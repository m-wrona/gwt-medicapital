package com.medicapital.client.pages;

import com.medicapital.client.pages.generic.ContactPage;
import com.medicapital.client.pages.generic.HelpPage;
import com.medicapital.client.pages.generic.MainPage;
import com.medicapital.client.pages.generic.OfferPage;
import com.medicapital.client.pages.generic.RegisterPage;
import com.medicapital.client.pages.generic.RegulationsPage;

final class PublicPageFactory extends PageFactory {

	@Override
	<T extends Page<?>> Page<?> createPage(Class<T> pageClass) {
		if (pageClass.equals(MainPage.class)) {
			return new MainPage();
		} else if (pageClass.equals(RegisterPage.class)) {
			return new RegisterPage();
		} else if (pageClass.equals(ContactPage.class)) {
			return new ContactPage();
		} else if (pageClass.equals(HelpPage.class)) {
			return new HelpPage();
		} else if (pageClass.equals(OfferPage.class)) {
			return new OfferPage();
		} else if (pageClass.equals(RegulationsPage.class)) {
			return new RegulationsPage();
		}
		return null;
	}

	@Override
	Page<?> createPage(final String pageName) {
		if (getPageName(MainPage.class).equals(pageName)) {
			return new MainPage();
		} else if (getPageName(RegisterPage.class).equals(pageName)) {
			return new RegisterPage();
		} else if (getPageName(ContactPage.class).equals(pageName)) {
			return new ContactPage();
		} else if (getPageName(HelpPage.class).equals(pageName)) {
			return new HelpPage();
		} else if (getPageName(OfferPage.class).equals(pageName)) {
			return new OfferPage();
		} else if (getPageName(RegulationsPage.class).equals(pageName)) {
			return new RegulationsPage();
		}
		return null;
	}

}
