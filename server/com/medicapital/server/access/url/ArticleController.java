package com.medicapital.server.access.url;

import static com.medicapital.server.log.Tracer.tracer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.server.logic.ArticleFacade;

public class ArticleController extends HttpController {

	private ArticleFacade articleFacade;
	private MultipartResolver multipartResolver;

	@Override
	protected ModelAndView handleHttpRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		final String context = req.getParameter(ArticleUrlService.PARAM_CONTEXT);
		if (context.equals(ArticleUrlService.CONTEXT_GET_ARTICLE_MAINPHOTO)) {
			return getArticleMainPhoto(req, resp);
		} else if (context.equals(ArticleUrlService.CONTEXT_UPLOAD_ARTICLE_MAIN_PHOTO)) {
			return uploadArticleMainPhoto(req, resp);
		} else if (context.equals(ArticleUrlService.CONTEXT_DELETE_ARTICLE_MAIN_PHOTO)) {
			return deleteArticleMainPhoto(req, resp);
		} else if (context.equals(ArticleUrlService.CONTEXT_GET_ARTICLE_ATTACHMENT)) {
			return getArticleAttachment(req, resp);
		} else if (context.equals(ArticleUrlService.CONTEXT_UPLOAD_ARTICLE_ATTACHMENT)) {
			return uploadArticleAttachment(req, resp);
		} else if (context.equals(ArticleUrlService.CONTEXT_DELETE_ARTICLE_ATTACHMENT)) {
			return deleteArticleAttachment(req, resp);
		}
		return null;
	}

	private ModelAndView uploadArticleAttachment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Upload article's attachment");
		MultipartHttpServletRequest multipartRequest = null;
		if (req instanceof MultipartHttpServletRequest) {
			tracer(this).debug("Request is an instance of: " + MultipartHttpServletRequest.class.getSimpleName());
			multipartRequest = (MultipartHttpServletRequest) req;
		} else {
			multipartRequest = multipartResolver.resolveMultipart(req);
		}
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		final String description = req.getParameter(ArticleUrlService.PARAM_ATTACHMENT_DESCRIPTION);
		final String type = req.getParameter(ArticleUrlService.PARAM_ATTACHMENT_TYPE);
		tracer(this).debug("Upload article's attachment - articleId: " + articleId);
		final MultipartFile attachFile = multipartRequest.getFile(ArticleUrlService.PARAM_ATTACHMENT);
		if (attachFile != null) {
			articleFacade.createArticleAttachment(articleId, attachFile.getBytes(), description, UrlResourceType.getType(type));
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		} else {
			tracer(this).error("Parameter not found: " + ArticleUrlService.PARAM_ATTACHMENT);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ArticleUrlService.STATUS_ERROR);
		}
		return null;
	}

	private ModelAndView deleteArticleAttachment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Delete article's attachment");
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		final String stringAttachId = req.getParameter(ArticleUrlService.PARAM_ATTACHMENT_ID);
		final int attachId = Integer.valueOf(stringAttachId);
		articleFacade.deleteArticleAttachment(articleId, attachId);
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	private ModelAndView getArticleAttachment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Get article's attachment");
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		final String stringAttachmentId = req.getParameter(ArticleUrlService.PARAM_ATTACHMENT_ID);
		final int attachmentId = Integer.valueOf(stringAttachmentId);
		tracer(this).debug("Get article's attachment - articleId: " + articleId + ", attachmentId: " + attachmentId);
		final byte[] profilePhoto = articleFacade.getArticleAttachment(articleId, attachmentId);
		if (profilePhoto != null) {
			tracer(this).debug("Attaching article image into HTTP response");
			resp.getOutputStream().write(profilePhoto);
		} else {
			tracer(this).debug("No photo found for article: " + articleId);
		}
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	private ModelAndView deleteArticleMainPhoto(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Deleting article's main photo");
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		tracer(this).debug("Deleting article's main photo - articleId: " + articleId);
		articleFacade.deleteArticleMainPhoto(articleId);
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	private ModelAndView uploadArticleMainPhoto(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Uploading article's main photo - is multipart: " + multipartResolver.isMultipart(req));
		MultipartHttpServletRequest multipartRequest = null;
		if (req instanceof MultipartHttpServletRequest) {
			tracer(this).debug("Request is an instance of: " + MultipartHttpServletRequest.class.getSimpleName());
			multipartRequest = (MultipartHttpServletRequest) req;
		} else {
			multipartRequest = multipartResolver.resolveMultipart(req);
		}
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		tracer(this).debug("Uploading article's main photo - articleId: " + articleId);
		final MultipartFile mainPhoto = multipartRequest.getFile(ArticleUrlService.PARAM_MAIN_PHOTO);
		if (mainPhoto != null) {
			articleFacade.saveArticleMainPhoto(articleId, mainPhoto.getBytes());
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		} else {
			tracer(this).error("Parameter not found: " + ArticleUrlService.PARAM_MAIN_PHOTO);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ArticleUrlService.STATUS_ERROR);
		}
		return null;

	}

	private ModelAndView getArticleMainPhoto(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		tracer(this).debug("Get article's main photo");
		final String stringArticleId = req.getParameter(ArticleUrlService.PARAM_ID);
		final int articleId = Integer.valueOf(stringArticleId);
		tracer(this).debug("Get article's main photo - articleId: " + articleId);
		final byte[] profilePhoto = articleFacade.getArticleMainPhoto(articleId);
		if (profilePhoto != null) {
			tracer(this).debug("Attaching main article image into HTTP response");
			resp.getOutputStream().write(profilePhoto);
		} else {
			tracer(this).debug("No main photo found for article: " + articleId);
		}
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	final public void setArticleFacade(ArticleFacade articleFacade) {
		this.articleFacade = articleFacade;
	}

	final public void setMultipartResolver(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

}
