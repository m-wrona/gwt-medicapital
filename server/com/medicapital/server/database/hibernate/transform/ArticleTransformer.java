package com.medicapital.server.database.hibernate.transform;

import java.util.Date;
import com.medicapital.common.entities.Article;
import com.medicapital.server.database.hibernate.entities.ArticleEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class ArticleTransformer implements Transformer<Article, ArticleEntity> {

	@Override
	public ArticleEntity createEntity(int entityId) {
		ArticleEntity entity = new ArticleEntity();
		entity.setArticleId(entityId);
		return entity;
	}

	@Override
	public Article createPojo(ArticleEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		Article article = new Article();
		article.setId(persistenceEntity.getArticleId());
		article.setTitle(persistenceEntity.getTitle());
		article.setBody(persistenceEntity.getBody());
		if (persistenceEntity.getCreated() != null) {
			article.setCreateDate(new Date(persistenceEntity.getCreated().getTime()));
		}
		if (persistenceEntity.getLastUpdated() != null) {
			article.setLastUpdate(new Date(persistenceEntity.getLastUpdated().getTime()));
		}
		article.setAuthorId(persistenceEntity.getAuthor().getUserId());
		article.setAuthorFirstName(persistenceEntity.getAuthor().getFirstName());
		article.setAuthorLastName(persistenceEntity.getAuthor().getLastName());
		article.setAuthorLogin(persistenceEntity.getAuthor().getLogin());
		article.setPublished(persistenceEntity.isPublished());
		return article;
	}

	@Override
	public ArticleEntity createEntity(Article pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		ArticleEntity article = new ArticleEntity();
		article.setArticleId(pojoEntity.getId());
		article.setTitle(pojoEntity.getTitle());
		article.setBody(pojoEntity.getBody());
		article.setCreated(pojoEntity.getCreateDate());
		article.setLastUpdated(pojoEntity.getLastUpdate());
		article.setPublished(pojoEntity.isPublished());
		UserEntity author = new UserEntity();
		article.setAuthor(author);
		author.setUserId(pojoEntity.getAuthorId());
		author.setLogin(pojoEntity.getAuthorLogin());
		author.setFirstName(pojoEntity.getAuthorFirstName());
		author.setLastName(pojoEntity.getAuthorLastName());
		return article;
	}
}
