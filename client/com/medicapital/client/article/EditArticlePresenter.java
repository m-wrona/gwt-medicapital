package com.medicapital.client.article;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.EditEntityPresenter;
import com.medicapital.client.log.Tracer;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;

public class EditArticlePresenter extends EditEntityPresenter<Article> {

	private final EditArticleView view;
	private EditArticleImagePresenter editImagePresenter;
	private EditArticleAttachmentsPresenter editAttachmentsPresenter;
	private boolean firstDisplay = true;

	public EditArticlePresenter(EditArticleView view, EventBus eventBus) {
		super(Article.class, view, eventBus);
		this.view = view;
	}

	@Override
	protected Article getEntityFromView() {
		return new ArticleViewBinder().getEntityFromView(view, getCurrentEntity());
	}

	@Override
	protected void displayEntityOnView(Article entity) {
		Tracer.tracer(this).debug("Displaying article on view: " + entity);
		validateImagePresenters();
		if (firstDisplay) {
			new ArticleViewBinder().display(view, entity);
			firstDisplay = false;
		}
		editImagePresenter.init(entity.getId(), new UrlResource());
		editImagePresenter.display();
		editAttachmentsPresenter.init(entity, editAttachmentsPresenter.getArticleUrlService(), editAttachmentsPresenter.getUrlResources());
		editAttachmentsPresenter.goToFirstPage();
	}

	@Override
	protected void clearView() {
		if (firstDisplay) {
			new ArticleViewBinder().clear(view);
		}
	}

	public void setEditAttachmentsPresenter(EditArticleAttachmentsPresenter editAttachmentsPresenter) {
		this.editAttachmentsPresenter = editAttachmentsPresenter;
	}

	public void setEditImagePresenter(EditArticleImagePresenter editImagePresenter) {
		this.editImagePresenter = editImagePresenter;
	}

	private void validateImagePresenters() throws IllegalArgumentException {
		if (editImagePresenter == null) {
			throw new IllegalArgumentException("Edit image presenter not set");
		} else if (editAttachmentsPresenter == null) {
			throw new IllegalArgumentException("Edit attachment presenter not set");
		}
	}

}
