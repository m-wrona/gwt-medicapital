package com.medicapital.client.article;

import com.medicapital.client.core.url.EditUrlResourcePresenter;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to edit article's main photo
 * 
 * @author mwronski
 * 
 */
class EditArticleImagePresenter extends EditUrlResourcePresenter {

	private final ArticleUrlService articleUrlService;

	public EditArticleImagePresenter(ArticleUrlService articleUrlService, GetterArticleView view) {
		super(view);
		this.articleUrlService = articleUrlService;
	}

	@Override
	protected String getUploadActionName(int articleId, UrlResource urlResource) {
		return articleUrlService.getUploadMainPhotoAction(articleId);
	}

	@Override
	protected String getDeleteActionName(int articleId) {
		return articleUrlService.getDeleteMainPhotoAction(articleId);
	}

	@Override
	protected String getUploadFileName() {
		return ArticleUrlService.PARAM_MAIN_PHOTO;
	}

	@Override
	protected String getUrlResource(int articleId) {
		return articleUrlService.getArticleMainPhoto(articleId);
	}

}
