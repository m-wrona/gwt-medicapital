package com.medicapital.client.article;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Collection;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entities.EntitiesPresenter;
import com.medicapital.client.pages.article.DisplayArticlePage;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;

public class ArticleListPresenter extends EntitiesPresenter<Article> {

	static final int SHORTCUT_LENGTH = 350;
	private final ArticleListView display;
	private ArticleUrlService articleUrlService;

	public ArticleListPresenter(ArticleListView display, EventBus eventBus) {
		super(Article.class, display, eventBus);
		this.display = display;
	}

	@Override
	protected void displayDataOnView(Collection<Article> data) {
		for (Article article : data) {
			String author = article.getAuthorLastName() + " " + article.getAuthorFirstName();
			String shortBody = article.getBody();
			if (shortBody.length() > SHORTCUT_LENGTH) {
				shortBody = shortBody.substring(0, SHORTCUT_LENGTH) + "...";
			}
			String photoUrl = null;
			if (articleUrlService != null) {
				photoUrl = articleUrlService.getArticleMainPhoto(article.getId());
			}
			display.addArticle(article.getId(), article.getTitle(), shortBody, author, article.getCreateDate(), article.getLastUpdate(), photoUrl);
		}

	}

	public void setArticleUrlService(ArticleUrlService articleUrlService) {
		this.articleUrlService = articleUrlService;
	}

	public void displayArticle(int articleId) {
		tracer(this).debug("Display details of article: " + articleId);
		getEventBus().fireEvent(DisplayArticlePage.createPageEvent(this, articleId));
	}

}
