package com.medicapital.client.article;

import com.medicapital.client.core.entity.EditEntityView;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;
import com.medicapital.common.entities.Article;

public interface EditArticleView extends EditEntityView<Article>, SetterArticleView, GetterArticleView {

	void setEditPresenter(EditArticlePresenter editArticlePresenter);
	
	EditUrlResourceListPresenterView getAttachmentView();
	
}
