package com.medicapital.client.article;

import java.util.Date;

import com.medicapital.client.core.entities.EditableEntitiesView;
import com.medicapital.common.entities.Article;

public interface EditableArticleListView extends EditableEntitiesView<Article> {

	void addArticle(int articleId, String title, String shortBody, Date created, Date lastUpdated, boolean isPublished);

	void setEditArticlesPresenter(EditableArticleListPresenter editableArticleListPresenter);
}
