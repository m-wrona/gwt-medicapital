package com.medicapital.client.dao;

import static com.medicapital.client.log.Tracer.tracer;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.common.util.UrlUtils;

public class ArticleUrlAccess implements ArticleUrlService {

	private static final String SERVICE_ENTRY_POINT = ServerAccess.SERVICE_MAIN_ENTRY_POINT + "articleController";

	public ArticleUrlAccess() {
		tracer(this).debug("Connection to article URL access - OK, service entry point: " + SERVICE_ENTRY_POINT);
	}

	@Override
	public String getArticleMainPhoto(int articleId) throws ServerException {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_GET_ARTICLE_MAINPHOTO);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Article id: " + articleId + ", URL article main picture: " + url);
		return url;
	}

	@Override
	public String getArticleAttachment(int articleId, int attachPhotoId) {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_GET_ARTICLE_ATTACHMENT);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_ATTACHMENT_ID, "" + attachPhotoId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Article id: " + articleId + ", attach photoId: " + attachPhotoId + ", URL article main picture: " + url);
		return url;
	}

	@Override
	public String getUploadMainPhotoAction(int articleId) {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_UPLOAD_ARTICLE_MAIN_PHOTO);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Upload main article photo - article id: " + articleId + ", action URL: " + url);
		return url;
	}

	@Override
	public String getDeleteMainPhotoAction(int articleId) {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_DELETE_ARTICLE_MAIN_PHOTO);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Delete main article photo - article id: " + articleId + ", action URL: " + url);
		return url;
	}

	@Override
	public String getUploadAttachmentAction(int articleId, String description, UrlResourceType urlResourceType) {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_UPLOAD_ARTICLE_ATTACHMENT);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_ATTACHMENT_DESCRIPTION, description);
		params.put(PARAM_ATTACHMENT_TYPE, urlResourceType.toString());
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Upload article's attachment - article id: " + articleId + ", action URL: " + url);
		return url;
	}

	@Override
	public String getDeleteAttachmentAction(int articleId, int attachmentId) {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_DELETE_ARTICLE_ATTACHMENT);
		params.put(PARAM_ID, "" + articleId);
		params.put(PARAM_ATTACHMENT_ID, "" + attachmentId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Delete article's attachment - article id: " + articleId + ", attachment id: " + attachmentId + ", action URL: " + url);
		return url;
	}

}
