package com.medicapital.client.pages;

import com.medicapital.client.pages.article.SearchArticleListPage;
import com.medicapital.client.pages.article.DisplayArticlePage;
import com.medicapital.client.pages.article.DoctorArticlesPage;
import com.medicapital.client.pages.user.UserHomePage;
import com.medicapital.client.pages.user.UserProfilePage;
import com.medicapital.client.pages.visit.UserVisitListPage;

final class UserPageFactory extends PageFactory {

	@Override
	<T extends Page<?>> Page<?> createPage(final Class<T> pageClass) {
		if (pageClass.equals(UserProfilePage.class)) {
			return new UserProfilePage();
		} else if (pageClass.equals(UserVisitListPage.class)) {
			return new UserVisitListPage();
		} else if (pageClass.equals(UserHomePage.class)) {
			return new UserHomePage();
		} else if (pageClass.equals(SearchArticleListPage.class)) {
			return new SearchArticleListPage();
		} else if (pageClass.equals(DisplayArticlePage.class)) {
			return new DisplayArticlePage();
		} else if (pageClass.equals(DoctorArticlesPage.class)) {
			return new DoctorArticlesPage();
		}
		return null;
	}

	@Override
	Page<?> createPage(final String pageName) {
		if (getPageName(UserProfilePage.class).equals(pageName)) {
			return new UserProfilePage();
		} else if (getPageName(UserVisitListPage.class).equals(pageName)) {
			return new UserVisitListPage();
		} else if (getPageName(UserHomePage.class).equals(pageName)) {
			return new UserHomePage();
		} else if (getPageName(DisplayArticlePage.class).equals(pageName)) {
			return new DisplayArticlePage();
		} else if (getPageName(DoctorArticlesPage.class).equals(pageName)) {
			return new DoctorArticlesPage();
		}
		return null;
	}

}
