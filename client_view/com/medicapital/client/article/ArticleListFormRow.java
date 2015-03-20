package com.medicapital.client.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableRow;

class ArticleListFormRow extends TableRow {

	private static ArticleListFormRowUiBinder uiBinder = GWT.create(ArticleListFormRowUiBinder.class);

	interface ArticleListFormRowUiBinder extends UiBinder<Widget, ArticleListFormRow> {
	}

	@UiField
	CheckBox selected;
	@UiField
	Anchor articleTitle;
	@UiField
	Label shortBody;
	@UiField
	Label created;
	@UiField
	Label lastUpdated;
	@UiField
	Label isPublished;

	public ArticleListFormRow() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelected(selected);
	}

	public Anchor getArticleTitle() {
		return articleTitle;
	}

	public Label getShortBody() {
		return shortBody;
	}

	public Label getCreated() {
		return created;
	}

	public Label getLastUpdated() {
		return lastUpdated;
	}

	public Label getIsPublished() {
		return isPublished;
	}

}
