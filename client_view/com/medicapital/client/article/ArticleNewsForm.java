package com.medicapital.client.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

class ArticleNewsForm extends Composite {

	private static ArticleNewsFormUiBinder uiBinder = GWT.create(ArticleNewsFormUiBinder.class);

	interface ArticleNewsFormUiBinder extends UiBinder<Widget, ArticleNewsForm> {
	}

	@UiField
	Anchor articleTitle;
	@UiField
	HTML articleBody;
	@UiField
	Label created;
	@UiField
	Label lastUpdate;
	@UiField
	HTMLPanel createPanel;
	@UiField
	HTMLPanel updatePanel;

	public ArticleNewsForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Anchor getArticleTitle() {
		return articleTitle;
	}

	public HTML getArticleBody() {
		return articleBody;
	}

	void setCreated(String date) {
		updatePanel.setVisible(false);
		created.setText(date);
	}
	
	void setLastUpdated(String date) {
		createPanel.setVisible(false);
		lastUpdate.setText(date);
	}

}
