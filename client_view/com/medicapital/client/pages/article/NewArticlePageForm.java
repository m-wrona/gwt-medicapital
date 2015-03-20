package com.medicapital.client.pages.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.article.CreateArticleForm;

public class NewArticlePageForm extends Composite {

	private static NewArticlePageFormUiBinder uiBinder = GWT.create(NewArticlePageFormUiBinder.class);

	interface NewArticlePageFormUiBinder extends UiBinder<Widget, NewArticlePageForm> {
	}

	@UiField
	CreateArticleForm createArticleForm;

	public NewArticlePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CreateArticleForm getCreateArticleForm() {
		return createArticleForm;
	}

}
