package com.medicapital.client.pages.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.article.DisplayArticleForm;

public class DisplayArticlePageForm extends Composite {

	private static DisplayArticlePageFormUiBinder uiBinder = GWT.create(DisplayArticlePageFormUiBinder.class);

	interface DisplayArticlePageFormUiBinder extends UiBinder<Widget, DisplayArticlePageForm> {
	}

	@UiField
	DisplayArticleForm articleForm;

	public DisplayArticlePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public DisplayArticleForm getArticleForm() {
		return articleForm;
	}

}
