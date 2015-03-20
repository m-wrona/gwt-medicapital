package com.medicapital.client.pages.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.article.ArticleListForm;
import com.medicapital.client.article.SearchArticleForm;

public class SearchArticleListPageForm extends Composite {

	private static ArticleListPageFormUiBinder uiBinder = GWT.create(ArticleListPageFormUiBinder.class);

	interface ArticleListPageFormUiBinder extends UiBinder<Widget, SearchArticleListPageForm> {
	}

	@UiField
	SearchArticleForm searchArticleForm;
	@UiField
	ArticleListForm articleList;

	public SearchArticleListPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ArticleListForm getArticleList() {
		return articleList;
	}
	
	public SearchArticleForm getSearchArticleForm() {
	    return searchArticleForm;
    }

}
