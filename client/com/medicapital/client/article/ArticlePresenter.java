package com.medicapital.client.article;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;

public class ArticlePresenter extends EntityPresenter<Article> {

	private final ArticleView view;
	private ArticleUrlService imageService;
	private ArticleAttachmentsPresenter imagesPresenter;

	public ArticlePresenter(ArticleView view, EventBus eventBus) {
		super(Article.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected void displayEntityOnView(Article entity) {
		new ArticleViewBinder().display(view, entity);
		if (imageService != null) {
			UrlResource image = new UrlResource();
			image.setUrl(imageService.getArticleMainPhoto(entity.getId()));
			view.display(image);
		}
		imagesPresenter = new ArticleAttachmentsPresenter(view.getPhotoListView(), imageService, entity);
		view.getPhotoListView().setUrlListPresenter(imagesPresenter);
		imagesPresenter.goToFirstPage();
	}

	@Override
	protected void clearView() {
		new ArticleViewBinder().clear(view);
	}

	public void setImageService(ArticleUrlService imageService) {
		this.imageService = imageService;
	}

	public ArticleUrlService getImageService() {
		return imageService;
	}

	final ArticleAttachmentsPresenter getImagesPresenter() {
		return imagesPresenter;
	}

}
