package com.medicapital.client.article;

import java.util.Date;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.Article;

public class ArticleListForm extends DataTable<ArticleListFormHeader, ArticleListFormRow, Article> implements ArticleListView {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private ArticleListPresenter articleListPresenter;

	@Override
	protected ArticleListFormHeader createHeader() {
		return new ArticleListFormHeader();
	}

	@Override
	public void addArticle(final int articleId, String title, String shortBody, String author, Date created, Date lastUpdated, String photoUrl) {
		ArticleListFormRow row = new ArticleListFormRow();
		row.getSelected().setVisible(false);
		row.setEditable(false);
		row.setRowId(articleId);
		row.getArticleTitle().setText(title);
		row.getShortBody().setText(shortBody);
		row.getCreated().setText(dateFormatter.toDateAndTimeString(created));
		row.getLastUpdated().setText(dateFormatter.toDateAndTimeString(lastUpdated));
		getHandlers().add(row.getArticleTitle().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (articleListPresenter != null) {
					articleListPresenter.displayArticle(articleId);
				}
			}
		}));
		addRow(row);
	}

	@Override
	public void setArticleListPresenter(ArticleListPresenter articleListPresenter) {
		this.articleListPresenter = articleListPresenter;
	}

}
