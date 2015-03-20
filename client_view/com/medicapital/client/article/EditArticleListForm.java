package com.medicapital.client.article;

import java.util.Date;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.Article;

public class EditArticleListForm extends DataTable<ArticleListFormHeader, ArticleListFormRow, Article> implements EditableArticleListView {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private EditableArticleListPresenter editableArticleListPresenter;

	public EditArticleListForm() {
		setDeleteSelectedButtonVisible(true);
		setSelectAllEnabled(true);
		setDeleteSelectedHandlerEnabled(false);
	}

	@Override
	protected ArticleListFormHeader createHeader() {
		ArticleListFormHeader header = new ArticleListFormHeader();
		header.getSelectAll().setVisible(true);
		return header;
	}

	@Override
	public void addArticle(final int articleId, String title, String shortBody, Date created, Date lastUpdated, boolean isPublished) {
		ArticleListFormRow row = new ArticleListFormRow();
		row.setRowId(articleId);
		row.getArticleTitle().setText(title);
		row.getShortBody().setText(shortBody);
		row.getCreated().setText(dateFormatter.toDateAndTimeString(created));
		row.getLastUpdated().setText(dateFormatter.toDateAndTimeString(lastUpdated));
		row.getIsPublished().setText("" + isPublished);
		getHandlers().add(row.getArticleTitle().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editableArticleListPresenter != null) {
					editableArticleListPresenter.editArticle(articleId);
				}
			}
		}));
		addRow(row);
	}

	@Override
	public void setEditArticlesPresenter(EditableArticleListPresenter editableArticleListPresenter) {
		this.editableArticleListPresenter = editableArticleListPresenter;
		setEditableEntitiesPresenter(editableArticleListPresenter);
	}

}
