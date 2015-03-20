package com.medicapital.client.article;

import com.medicapital.client.core.entity.EntityView;
import com.medicapital.client.core.url.UrlResourceListView;

public interface ArticleView extends SetterArticleView, EntityView {

	UrlResourceListView getPhotoListView();
}
