package com.medicapital.server.database;

import java.util.Collection;
import java.util.List;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.UrlResourceType;

public interface DaoArticleAttachments {

	List<UrlResource> getArticleAttachments(int articleId) throws DataAccessException;

	void getArticleAttachments(Article article) throws DataAccessException;

	void getArticleAttachments(Collection<Article> articles) throws DataAccessException;

	/**
	 * Delete article's attachment
	 * 
	 * @param articleId
	 * @param attachmentId
	 * @throws DataAccessException
	 */
	void deleteArticleAttachment(int articleId, int attachmentId) throws DataAccessException;

	/**
	 * Create article's attachment
	 * 
	 * @param articleId
	 * @param value
	 * @param description
	 * @param urlResourceType
	 * @return ID of create attachment
	 * @throws DataAccessException
	 */
	int createArticleAttachment(int articleId, byte[] value, String description, UrlResourceType urlResourceType) throws DataAccessException;

	/**
	 * Get article attachment
	 * 
	 * @param articleId
	 * @param attachmentId
	 * @return
	 * @throws DataAccessException
	 */
	byte[] getArticleAttachment(int articleId, int attachmentId) throws DataAccessException;
}
