package com.medicapital.client.pages.article;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.medicapital.client.article.ArticleFactory;
import com.medicapital.client.article.EditArticlePresenter;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;
import com.medicapital.client.pages.history.PageHistory;
import com.medicapital.common.commands.article.SelectArticlesCommand;
import com.medicapital.common.commands.article.SelectArticlesCountCommand;
import com.medicapital.common.util.MapUtils;

public class EditArticlePage extends DoctorPage<EditArticlePageForm> {

	public static final String PARAM_ARTICLE_ID = "ArticleId";
	private final ArticleFactory articleFactory = new ArticleFactory();
	private EditArticlePresenter editArticlePresenter;

	@Override
	protected EditArticlePageForm createPageView() {
		return new EditArticlePageForm();
	}

	@Override
	protected void initPage(EditArticlePageForm pageView) {
		SelectArticlesCountCommand selectArticlesCountCommand = new SelectArticlesCountCommand();
		selectArticlesCountCommand.setLogin(getLoggedUser());
		selectArticlesCountCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCountCommand(selectArticlesCountCommand);
		SelectArticlesCommand selectArticlesCommand = new SelectArticlesCommand();
		selectArticlesCommand.setLogin(getLoggedUser());
		selectArticlesCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCommand(selectArticlesCommand);
		editArticlePresenter = articleFactory.createEditArticlePresenter(pageView.getEditArticleForm());
		editArticlePresenter.setAfterDeleteCommand(new Command() {

			@Override
			public void execute() {
				PageHistory.back();
			}
		});
		pageView.getEditArticleForm().getButtonCancel().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PageHistory.back();

			}
		});
	}

	@Override
	public void loadState(Map<String, String> pageStateParameters) {
		int articleId = MapUtils.getInt(pageStateParameters, PARAM_ARTICLE_ID, false);
		articleFactory.getCommandFactory().setEntityId(articleId);
		editArticlePresenter.refreshDisplay();
	}

	public static DisplayPageEvent createPageEvent(final Object sender, int articleId) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, EditArticlePage.class);
		pageEvent.addRequestParameter(PARAM_ARTICLE_ID, "" + articleId);
		return pageEvent;
	}

}
