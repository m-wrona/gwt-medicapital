package com.medicapital.client.article;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Date;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.User;

public class CreateArticlePresenter extends CreateEntityPresenter<Article> {

	private final CreateArticleView view;
	private EditArticleImagePresenter editImagePresenter;
	private EditArticleAttachmentsPresenter editAttachmentsPresenter;
	private boolean firstSave = true;
	/**
	 * Flag indicates whether element was created by user or not. If it was not
	 * created then when canceling operation temporary created article should be
	 * cleaned up in service.
	 */
	private boolean elementCreatedByUser = false;

	public CreateArticlePresenter(CreateArticleView view, EventBus eventBus) {
		super(Article.class, view, eventBus);
		this.view = view;
	}

	/**
	 * Initialize presenter with needed data to create entity
	 * 
	 * @param userId
	 * @throws IllegalArgumentException
	 */
	public void init(int userId) throws IllegalArgumentException {
		if (getServiceAccess() == null) {
			throw new IllegalArgumentException("Service access not set");
		}
		view.setViewBlocked(true);
		tracer(this).debug("Initializing presenter by getting user info");
		SelectCommand<User> selectUser = new SelectCommand<User>(User.class, userId);
		getServiceAccess().execute(selectUser, new ResponseHandler<User>() {

			@Override
			public void handle(CommandResp<User> response) {
				if (response instanceof SelectCommandResp) {
					SelectCommandResp<User> selectResponse = (SelectCommandResp<User>) response;
					if (selectResponse.getCount() > 0) {
						Article article = new Article();
						User author = selectResponse.getData().iterator().next();
						tracer(this).debug("Initializing presenter - author data: " + author);
						article.setAuthorId(author.getId());
						article.setAuthorFirstName(author.getFirstName());
						article.setAuthorLastName(author.getLastName());
						article.setAuthorLogin(author.getLogin());
						article.setPublished(false);
						article.setCreateDate(new Date());
						display(article);
					}
					view.setViewBlocked(false);
					saveChanges();
				}

			}

			@Override
			public void handleException(Throwable throwable) {
				view.setViewBlocked(false);
			}
		});
	}

	/**
	 * Cancel create operation
	 */
	public void cancel() {
		if (!elementCreatedByUser && getCurrentEntity().getId() > 0) {
			tracer(this).debug("Deleting temporary created article - articleId: " + getCurrentEntity().getId());
			DeleteCommand<Article> cleanCommand = new DeleteCommand<Article>(Article.class, getCurrentEntity().getId());
			view.setViewBlocked(true);
			getServiceAccess().execute(cleanCommand, new ResponseHandler<Article>() {

				@Override
				public void handle(CommandResp<Article> response) {
					if (response instanceof DeleteCommandResp) {
						DeleteCommandResp<Article> deleteResponse = (DeleteCommandResp<Article>) response;
						if (deleteResponse.getCount() > 0) {
							tracer(this).debug("Deleting temporary created article - OK");
							display((Article) null);
							elementCreatedByUser = false;
							firstSave = true;
						}
						view.setViewBlocked(false);
					}
				}

				@Override
				public void handleException(Throwable throwable) {
					view.setViewBlocked(false);
				}
			});
		}
		// TODO redirect to article list
	}

	/**
	 * Save changes done in article's data (auto-save)
	 */
	public void saveChanges() {
		tracer(this).debug("Saving changes...");
		if (firstSave) {
			super.create();
		} else {
			saveChanges(true);
		}
	}

	@Override
	protected void afterEntityCreated(Article createdElement) {
		super.afterEntityCreated(createdElement);
		view.setLastUpdateDate(new Date());
		firstSave = false;
	}

	@Override
	public void create() {
		elementCreatedByUser = true;
		if (firstSave) {
			super.create();
		} else {
			saveChanges(false);
		}
	}

	/**
	 * Save changed data in article
	 * 
	 * @param autosave
	 * 
	 */
	private void saveChanges(final boolean autosave) {
		final Article element = getEntityFromView();
		if (autosave) {
			element.setPublished(false);
		}
		tracer(this).debug("Saving edited element: " + element);
		validatePresenter();
		if (element == null || !validateElement(element)) {
			tracer(this).debug("Edited element is not valid - saving canceled");
			return;
		}
		view.setViewBlocked(true);
		final UpdateCommand<Article> updateCommand = new UpdateCommand<Article>(Article.class, element);
		getServiceAccess().execute(updateCommand, new ResponseHandler<Article>() {

			@Override
			public void handle(final CommandResp<Article> response) {
				if (response instanceof UpdateCommandResp) {
					UpdateCommandResp<Article> updatedResponse = (UpdateCommandResp<Article>) response;
					if (updatedResponse.getCount() > 0) {
						view.setLastUpdateDate(new Date());
					}
					view.setViewBlocked(false);
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				view.setViewBlocked(false);
			}
		});
	}

	@Override
	protected Article getEntityFromView() {
		return new ArticleViewBinder().getEntityFromView(view, getCurrentEntity());
	}

	@Override
	protected void displayEntityOnView(Article entity) {
		validateImagePresenters();
		if (firstSave) {
			new ArticleViewBinder().display(view, entity);
		}
		editImagePresenter.init(entity.getId(), new UrlResource());
		editAttachmentsPresenter.init(entity, editAttachmentsPresenter.getArticleUrlService(), editAttachmentsPresenter.getUrlResources());
		if (!firstSave) {
			editImagePresenter.display();
			editAttachmentsPresenter.goToFirstPage();
		}
	}

	@Override
	protected void clearView() {
		if (firstSave) {
			new ArticleViewBinder().clear(view);
		}
	}

	/**
	 * Is element created by user
	 * 
	 * @return true if article was created by user, otherwise article was
	 *         created temporary
	 */
	protected boolean isElementCreatedByUser() {
		return elementCreatedByUser;
	}

	public void setEditImagePresenter(EditArticleImagePresenter editImagePresenter) {
		this.editImagePresenter = editImagePresenter;
	}

	public void setEditAttachmentsPresenter(EditArticleAttachmentsPresenter editAttachmentsPresenter) {
		this.editAttachmentsPresenter = editAttachmentsPresenter;
	}

	private void validateImagePresenters() throws IllegalArgumentException {
		if (editImagePresenter == null) {
			throw new IllegalArgumentException("Edit image presenter not set");
		} else if (editAttachmentsPresenter == null) {
			throw new IllegalArgumentException("Edit attachment presenter not set");
		}
	}
}
