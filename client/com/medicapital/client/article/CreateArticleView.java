package com.medicapital.client.article;

import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;
import com.medicapital.common.entities.Article;

public interface CreateArticleView extends CreateEntityView<Article>, SetterArticleView, GetterArticleView {

	EditUrlResourceListPresenterView getAttachmentView();

	void setCreatePresenter(CreateArticlePresenter createArticlePresenter);

}
