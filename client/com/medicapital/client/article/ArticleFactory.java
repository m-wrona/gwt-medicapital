package com.medicapital.client.article;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.Article;

final public class ArticleFactory {

	private final EntityCommandFactory<Article> commandFactory = new EntityCommandFactory<Article>(Article.class);

	public ArticleListPresenter createArticleListPresenter(ArticleListView view) {
		ArticleListPresenter articleListPresenter = new ArticleListPresenter(view, DaoFactory.getEventBus());
		articleListPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		articleListPresenter.setDisplayCommandFactory(commandFactory);
		articleListPresenter.setArticleUrlService(DaoFactory.getArticleUrlService());
		view.setArticleListPresenter(articleListPresenter);
		return articleListPresenter;
	}

	public EditableArticleListPresenter createEditArticleListPresenter(EditableArticleListView view) {
		EditableArticleListPresenter editableArticleListPresenter = new EditableArticleListPresenter(view, DaoFactory.getEventBus());
		editableArticleListPresenter.setDisplayCommandFactory(commandFactory);
		editableArticleListPresenter.setEditCommandFactory(commandFactory);
		editableArticleListPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		view.setEditArticlesPresenter(editableArticleListPresenter);
		return editableArticleListPresenter;
	}

	public ArticlePresenter createDisplayArticlePresenter(ArticleView view) {
		ArticlePresenter articlePresenter = new ArticlePresenter(view, DaoFactory.getEventBus());
		articlePresenter.setServiceAccess(DaoFactory.getServiceAccess());
		articlePresenter.setDisplayCommandFactory(commandFactory);
		articlePresenter.setImageService(DaoFactory.getArticleUrlService());
		return articlePresenter;
	}

	public EditArticlePresenter createEditArticlePresenter(EditArticleView view) {
		EditArticlePresenter editArticlePresenter = new EditArticlePresenter(view, DaoFactory.getEventBus());
		editArticlePresenter.setServiceAccess(DaoFactory.getServiceAccess());
		editArticlePresenter.setEditCommandFactory(commandFactory);
		editArticlePresenter.setDisplayCommandFactory(commandFactory);
		view.setEditPresenter(editArticlePresenter);
		// image support
		editArticlePresenter.setEditImagePresenter(createEditArticleImagePresenter(view));
		EditArticleAttachmentsPresenter editAttachmentPresenter = createEditArticleAttachmentsPresenter(editArticlePresenter, view.getAttachmentView());
		editArticlePresenter.setEditAttachmentsPresenter(editAttachmentPresenter);
		view.getAttachmentView().setEditUrlResourceListPresenter(editAttachmentPresenter);
		return editArticlePresenter;
	}

	public CreateArticlePresenter createCreateArticlePresenter(CreateArticleView view) {
		CreateArticlePresenter createArticlePresenter = new CreateArticlePresenter(view, DaoFactory.getEventBus());
		createArticlePresenter.setServiceAccess(DaoFactory.getServiceAccess());
		createArticlePresenter.setCreateCommandFactory(commandFactory);
		createArticlePresenter.setDisplayCommandFactory(commandFactory);
		// image support
		createArticlePresenter.setEditImagePresenter(createEditArticleImagePresenter(view));
		EditArticleAttachmentsPresenter editAttachmentPresenter = createEditArticleAttachmentsPresenter(createArticlePresenter, view.getAttachmentView());
		createArticlePresenter.setEditAttachmentsPresenter(editAttachmentPresenter);
		view.getAttachmentView().setEditUrlResourceListPresenter(editAttachmentPresenter);
		view.setCreatePresenter(createArticlePresenter);
		return createArticlePresenter;
	}

	private EditArticleImagePresenter createEditArticleImagePresenter(GetterArticleView getterArticleView) {
		EditArticleImagePresenter editArticleImagePresenter = new EditArticleImagePresenter(DaoFactory.getArticleUrlService(), getterArticleView);
		getterArticleView.setEditUrlResourceListPresenter(editArticleImagePresenter);
		return editArticleImagePresenter;
	}

	private EditArticleAttachmentsPresenter createEditArticleAttachmentsPresenter(EntityPresenter<Article> articlePresenter, EditUrlResourceListPresenterView view) {
		return new EditArticleAttachmentsPresenter(articlePresenter, view, DaoFactory.getArticleUrlService());
	}

	public EntityCommandFactory<Article> getCommandFactory() {
		return commandFactory;
	}
}
