package com.medicapital.client.pages.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.article.EditArticleForm;

public class EditArticlePageForm extends Composite {

	private static EditArticlePageFormUiBinder uiBinder = GWT.create(EditArticlePageFormUiBinder.class);

	interface EditArticlePageFormUiBinder extends UiBinder<Widget, EditArticlePageForm> {
	}

	@UiField
	EditArticleForm editArticleForm;

	public EditArticlePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public EditArticleForm getEditArticleForm() {
		return editArticleForm;
	}

}
