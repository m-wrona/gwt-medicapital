package com.medicapital.server.database.hibernate;

import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.server.database.DaoArticleAttachments;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.ArticleAttachmentsEntity;
import com.medicapital.server.database.hibernate.transform.ArticleAttachmentTransformer;

public class HibernateArticleAttachments extends HibernateAccess implements DaoArticleAttachments {

	@Override
	public int createArticleAttachment(int articleId, byte[] value, String description, UrlResourceType urlResourceType) throws DataAccessException {
		try {
			ArticleAttachmentsEntity attachment = new ArticleAttachmentsEntity();
			attachment.setArticleId(articleId);
			attachment.setDescription(description);
			attachment.setType(urlResourceType.toString());
			attachment.setValue(value);
			return (Integer) getHibernateTemplate().save(attachment);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void deleteArticleAttachment(int articleId, int attachmentId) throws DataAccessException {
		try {
			final Query query = getSession().createSQLQuery("delete from articleattachments where articleId=:articleId and attachmentId=:attachmentId");
			query.setInteger("articleId", articleId);
			query.setInteger("attachmentId", attachmentId);
			query.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte[] getArticleAttachment(int articleId, int attachmentId) throws DataAccessException {
		try {
			final Query query = getSession().createSQLQuery("select value from articleattachments where articleId = :articleId and attachmentId=:attachmentId");
			query.setInteger("articleId", articleId);
			query.setInteger("attachmentId", attachmentId);
			return (byte[]) query.uniqueResult();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void getArticleAttachments(Article article) throws DataAccessException {
		article.setAttachments(getArticleAttachments(article.getId()));
	}

	@Override
	public void getArticleAttachments(Collection<Article> articles) throws DataAccessException {
		// TODO to be optimized
		for (Article article : articles) {
			getArticleAttachments(article);
		}
	}

	@Override
	public List<UrlResource> getArticleAttachments(int articleId) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + ArticleAttachmentsEntity.class.getSimpleName() + " where articleId=:articleId");
			query.setInteger("articleId", articleId);
			@SuppressWarnings("unchecked")
			final List<ArticleAttachmentsEntity> result = query.list();
			return HibernateDaoUtil.toPojoList(result, new ArticleAttachmentTransformer());
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

}
