package com.medicapital.client.pages.article;

import java.util.HashMap;
import java.util.Map;
import com.medicapital.client.article.ArticleFactory;
import com.medicapital.client.article.ArticlePresenter;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.UserPage;
import com.medicapital.common.util.MapUtils;

final public class DisplayArticlePage extends UserPage<DisplayArticlePageForm> {

	public static final String PAGE_NAME = "DisplayArticle";
	private static final String PARAM_ARTICLE_ID = "ArticleId";
	private final ArticleFactory articleFactory = new ArticleFactory();
	private int articleId;
	private ArticlePresenter articlePresenter;

	@Override
	protected DisplayArticlePageForm createPageView() {
		return new DisplayArticlePageForm();
	}

	@Override
	protected void initPage(DisplayArticlePageForm pageView) {
		articlePresenter = articleFactory.createDisplayArticlePresenter(pageView.getArticleForm());
		getPresenters().add(articlePresenter);
	}

	@Override
	final public void loadState(Map<String, String> pageStateParameters) {
		articleId = MapUtils.getInt(pageStateParameters, PARAM_ARTICLE_ID, false);
		articleFactory.getCommandFactory().setEntityId(articleId);
		articlePresenter.refreshDisplay();
	}

	@Override
	final public Map<String, String> saveState() {
		Map<String, String> state = new HashMap<String, String>();
		state.put(PARAM_ARTICLE_ID, "" + articleId);
		return state;
	}

	public static DisplayPageEvent createPageEvent(Object sender, int articleId) {
		DisplayPageEvent displayPageEvent = new DisplayPageEvent(sender, DisplayArticlePage.class);
		displayPageEvent.addRequestParameter(PARAM_ARTICLE_ID, "" + articleId);
		return displayPageEvent;
	}

}
