package com.medicapital.client.article;

import java.util.List;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.client.core.url.EditUrlResourceListPresenter;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to manage article's attachments
 * 
 * @author mwronski
 * 
 */
class EditArticleAttachmentsPresenter extends EditUrlResourceListPresenter<Article, ArticleUrlService> {

	private final ArticleUrlService articleUrlService;

	public EditArticleAttachmentsPresenter(EntityPresenter<Article> masterPresenter, EditUrlResourceListPresenterView view, ArticleUrlService urlService) {
		super(masterPresenter, view, urlService);
		this.articleUrlService = urlService;
	}

	@Override
	protected void init(Article article, ArticleUrlService imageAccess, List<UrlResource> urlResources) {
		getUrlResources().clear();
		if (article != null && article.getAttachmentsCount() > 0) {
			for (UrlResource image : article.getAttachments()) {
				image.setUrl(imageAccess.getArticleAttachment(article.getId(), image.getId()));
				urlResources.add(image);
			}
			setTotalRows(article.getAttachmentsCount());
		}
	}

	@Override
	protected String getUploadActionName(int articleId, UrlResource urlResource) {
		return articleUrlService.getUploadAttachmentAction(articleId, urlResource.getDescription(), urlResource.getUrlResourceType());
	}

	@Override
	protected String getDeleteActionName(int articleId, int attachmentId) {
		return articleUrlService.getDeleteAttachmentAction(articleId, attachmentId);
	}

	@Override
	protected String getUploadFileName() {
		return ArticleUrlService.PARAM_ATTACHMENT;
	}

	public ArticleUrlService getArticleUrlService() {
		return articleUrlService;
	}

	@Override
	protected List<UrlResource> getUrlResources() {
		return super.getUrlResources();
	}

}
