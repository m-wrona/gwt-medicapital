package com.medicapital.client.article;

import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.url.EditUrlResourcePresenterView;

interface GetterArticleView extends WidgetView, EditUrlResourcePresenterView {

	String getArticleTitle();

	String getAtricleBody();

	boolean isPublished();

}
