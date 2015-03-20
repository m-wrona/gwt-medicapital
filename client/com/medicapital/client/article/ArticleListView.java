package com.medicapital.client.article;

import java.util.Date;

import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.common.entities.Article;

public interface ArticleListView extends EntitiesView<Article> {
	
	void addArticle(int articleId, String title, String shortBody, String author, Date created, Date lastUpdated, String photoUrl);
	
	void setArticleListPresenter(ArticleListPresenter articleListPresenter);

}
