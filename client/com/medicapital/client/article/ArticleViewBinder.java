package com.medicapital.client.article;

import com.medicapital.common.entities.Article;

class ArticleViewBinder {

	public void display(SetterArticleView view, Article article) {
		view.setArticleTitle(article.getTitle());
		view.setArticleBody(article.getBody());
		view.setAuthorFirstName(article.getAuthorFirstName());
		view.setAuthorLastName(article.getAuthorLastName());
		view.setCreateDate(article.getCreateDate());
		view.setLastUpdateDate(article.getLastUpdate());
		view.setPublished(article.isPublished());
	}

	public void clear(SetterArticleView view) {
		view.setArticleTitle("");
		view.setArticleBody("");
		view.setAuthorFirstName("");
		view.setAuthorLastName("");
		view.setCreateDate(null);
		view.setLastUpdateDate(null);
		view.setPublished(false);
	}

	public Article getEntityFromView(GetterArticleView view, Article oldArticle) {
		Article article = new Article();
		article.setTitle(view.getArticleTitle());
		article.setBody(view.getAtricleBody());
		article.setPublished(view.isPublished());
		if (oldArticle != null) {
			article.setId(oldArticle.getId());
			article.setAuthorId(oldArticle.getAuthorId());
			article.setAuthorFirstName(oldArticle.getAuthorFirstName());
			article.setAuthorLastName(oldArticle.getAuthorLastName());
			article.setAuthorLogin(oldArticle.getAuthorLogin());
			article.setAttachments(oldArticle.getAttachments());
			article.setCreateDate(oldArticle.getCreateDate());
			article.setLastUpdate(oldArticle.getLastUpdate());
		}
		return article;
	}

}
