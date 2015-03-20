package com.medicapital.client.pages.article;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.article.ArticleFactory;
import com.medicapital.client.article.ArticleListPresenter;
import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.UserPage;
import com.medicapital.common.commands.article.SearchArticleCommand;
import com.medicapital.common.commands.article.SearchArticleCountCommand;
import com.medicapital.common.entities.Article;

public class SearchArticleListPage extends UserPage<SearchArticleListPageForm> {

	private final ArticleFactory articleFactory = new ArticleFactory();
	private ArticleListPresenter articleListPresenter;

	@Override
	protected SearchArticleListPageForm createPageView() {
		return new SearchArticleListPageForm();
	}

	@Override
	protected void initPage(SearchArticleListPageForm pageView) {
		articleListPresenter = articleFactory.createArticleListPresenter(pageView.getArticleList());
		getPresenters().add(articleListPresenter);
		pageView.getSearchArticleForm().getButtonSearch().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getSearchCriteria(articleFactory.getCommandFactory());
				articleListPresenter.setDisplayCommandFactory(articleFactory.getCommandFactory());
				articleListPresenter.goToFirstPage();
			}
		});
	}

	private void getSearchCriteria(EntityCommandFactory<Article> commandFactory) {
		SearchArticleCountCommand searchArticleCountCommand = new SearchArticleCountCommand();
		searchArticleCountCommand.setTitle(getPageView().getSearchArticleForm().getArticleTitle());
		searchArticleCountCommand.setCreatedFrom(getPageView().getSearchArticleForm().getCreatedFrom());
		searchArticleCountCommand.setCreatedTo(getPageView().getSearchArticleForm().getCreatedTo());
		searchArticleCountCommand.setFirstName(getPageView().getSearchArticleForm().getFirstName());
		searchArticleCountCommand.setLastName(getPageView().getSearchArticleForm().getLastName());
		commandFactory.setSelectCountCommand(searchArticleCountCommand);

		SearchArticleCommand searchArticleCommand = new SearchArticleCommand();
		searchArticleCommand.setTitle(getPageView().getSearchArticleForm().getArticleTitle());
		searchArticleCommand.setCreatedFrom(getPageView().getSearchArticleForm().getCreatedFrom());
		searchArticleCommand.setCreatedTo(getPageView().getSearchArticleForm().getCreatedTo());
		searchArticleCommand.setFirstName(getPageView().getSearchArticleForm().getFirstName());
		searchArticleCommand.setLastName(getPageView().getSearchArticleForm().getLastName());
		searchArticleCommand.setStartRow(0);
		searchArticleCommand.setRowCount(10);
		commandFactory.setSelectCommand(searchArticleCommand);
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, SearchArticleListPage.class);
		return pageEvent;
	}

}
