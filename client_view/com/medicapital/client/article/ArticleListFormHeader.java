package com.medicapital.client.article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

class ArticleListFormHeader extends TableHeader {

	private static ArticleListFormHeaderUiBinder uiBinder = GWT.create(ArticleListFormHeaderUiBinder.class);

	interface ArticleListFormHeaderUiBinder extends UiBinder<Widget, ArticleListFormHeader> {
	}

	@UiField
	CheckBox selectAll;

	public ArticleListFormHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelectedAll(selectAll);
	}

	public CheckBox getSelectAll() {
		return selectAll;
	}

}
