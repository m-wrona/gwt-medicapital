package com.medicapital.client.pages.article;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.article.ArticleFactory;
import com.medicapital.client.article.CreateArticlePresenter;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;
import com.medicapital.client.pages.history.PageHistory;
import com.medicapital.common.commands.article.SelectArticlesCommand;
import com.medicapital.common.commands.article.SelectArticlesCountCommand;

public class NewArticlePage extends DoctorPage<NewArticlePageForm> {

	@Override
	protected NewArticlePageForm createPageView() {
		return new NewArticlePageForm();
	}

	@Override
	protected void initPage(NewArticlePageForm pageView) {
		final ArticleFactory articleFactory = new ArticleFactory();
		SelectArticlesCountCommand selectArticlesCountCommand = new SelectArticlesCountCommand();
		selectArticlesCountCommand.setLogin(getLoggedUser());
		selectArticlesCountCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCountCommand(selectArticlesCountCommand);
		SelectArticlesCommand selectArticlesCommand = new SelectArticlesCommand();
		selectArticlesCommand.setLogin(getLoggedUser());
		selectArticlesCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCommand(selectArticlesCommand);
		final CreateArticlePresenter createArticlePresenter = articleFactory.createCreateArticlePresenter(pageView.getCreateArticleForm());
		createArticlePresenter.init(getLoggedUserId());
		getPresenters().add(createArticlePresenter);
		pageView.getCreateArticleForm().getButtonCancel().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PageHistory.back();

			}
		});
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, NewArticlePage.class);
		return pageEvent;
	}

}
