package com.medicapital.client.article;

import java.util.Collection;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entities.EditableEntitiesPresenter;
import com.medicapital.client.pages.article.EditArticlePage;
import com.medicapital.common.entities.Article;

public class EditableArticleListPresenter extends EditableEntitiesPresenter<Article> {

	private final EditableArticleListView view;

	public EditableArticleListPresenter(EditableArticleListView view, EventBus eventBus) {
		super(Article.class, view, eventBus);
		this.view = view;
	}

	/**
	 * Edit article
	 * 
	 * @param articleId
	 * @throws IllegalArgumentException
	 *             when article doesn't belong to presenter or presenter is not
	 *             fully initialized
	 */
	public void editArticle(int articleId) throws IllegalArgumentException {
		if (!getDisplayedElements().containsKey(articleId)) {
			throw new IllegalArgumentException("Article " + articleId + " isn't displayed by presenter");
		}
		getEventBus().fireEvent(EditArticlePage.createPageEvent(this, articleId));
	}

	@Override
	protected void displayDataOnView(Collection<Article> data) {
		for (Article article : data) {
			String shortBody = article.getBody();
			if (shortBody.length() > ArticleListPresenter.SHORTCUT_LENGTH) {
				shortBody = shortBody.substring(0, ArticleListPresenter.SHORTCUT_LENGTH) + "...";
			}
			view.addArticle(article.getId(), article.getTitle(), shortBody, article.getCreateDate(), article.getLastUpdate(), article.isPublished());
		}
	}

}
