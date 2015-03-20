package com.medicapital.client.pages.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.article.EditArticleListForm;

public class DoctorArticlesPageForm extends Composite {

	private static DoctorArticlesPageFormUiBinder uiBinder = GWT.create(DoctorArticlesPageFormUiBinder.class);

	interface DoctorArticlesPageFormUiBinder extends UiBinder<Widget, DoctorArticlesPageForm> {
	}

	@UiField
	EditArticleListForm editArticleListForm;

	public DoctorArticlesPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public EditArticleListForm getEditArticleListForm() {
		return editArticleListForm;
	}

}
