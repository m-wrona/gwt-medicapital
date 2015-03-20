package com.medicapital.common.dao;

import com.medicapital.common.entities.UrlResourceType;

public interface ArticleUrlService extends UrlService {

	/**
	 * Context of request
	 */
	String PARAM_CONTEXT = "context";

	/**
	 * Get article's main photo
	 */
	String CONTEXT_GET_ARTICLE_MAINPHOTO = "getArticleMainPhoto";

	/**
	 * Upload article's main photo
	 */
	String CONTEXT_UPLOAD_ARTICLE_MAIN_PHOTO = "uploadArticleMainPhoto";

	/**
	 * Delete article's main photo
	 */
	String CONTEXT_DELETE_ARTICLE_MAIN_PHOTO = "deleteArticleMainPhoto";

	/**
	 * Get article's attachment
	 */
	String CONTEXT_GET_ARTICLE_ATTACHMENT = "getArticleAttachment";

	/**
	 * Upload article's attachment
	 */
	String CONTEXT_UPLOAD_ARTICLE_ATTACHMENT = "uploadArticleAttachment";

	/**
	 * Delete article's attachment
	 */
	String CONTEXT_DELETE_ARTICLE_ATTACHMENT = "deleteArticleAttachment";

	/**
	 * Primary ID
	 */
	String PARAM_ID = "id";

	/**
	 * Photo order number
	 */
	String PARAM_ATTACHMENT_ID = "attachmentId";

	String PARAM_ATTACHMENT_DESCRIPTION = "description";

	String PARAM_ATTACHMENT_TYPE = "type";

	String PARAM_MAIN_PHOTO = "mainArticlePhoto";

	String PARAM_ATTACHMENT = "attachment";

	/**
	 * Get article's attachment
	 * 
	 * @param articleId
	 * @param attachPhotoId
	 * @return
	 */
	String getArticleAttachment(int articleId, int attachPhotoId);

	/**
	 * Get article's main photo
	 * 
	 * @param articleId
	 * @return
	 * @throws ServerException
	 */
	String getArticleMainPhoto(int articleId);

	/**
	 * Get action's URL for uploading article's main photo
	 * 
	 * @param articleId
	 * @return
	 */
	String getUploadMainPhotoAction(int articleId);

	/**
	 * Get action's URL for deleting article's main photo
	 * 
	 * @param articleId
	 * @return
	 */
	String getDeleteMainPhotoAction(int articleId);

	/**
	 * Get action's URL for uploading article's attachment
	 * 
	 * @param articleId
	 * @param description
	 * @param urlResourceType
	 * @return
	 */
	String getUploadAttachmentAction(int articleId, String description, UrlResourceType urlResourceType);

	/**
	 * Get action's URL for deleting article's attachment
	 * 
	 * @param articleId
	 * @param attachmentId
	 * @return
	 */
	String getDeleteAttachmentAction(int articleId, int attachmentId);
}
