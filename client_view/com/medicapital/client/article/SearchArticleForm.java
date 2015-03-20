package com.medicapital.client.article;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.medicapital.client.ui.PopupableComposite;

public class SearchArticleForm extends PopupableComposite {

	private static SearchArticleFormUiBinder uiBinder = GWT.create(SearchArticleFormUiBinder.class);

	interface SearchArticleFormUiBinder extends UiBinder<Widget, SearchArticleForm> {
	}

	@UiField
	TextBox firstName;
	@UiField
	TextBox lastName;
	@UiField
	TextBox title;
	@UiField
	DateBox createdFrom;
	@UiField
	DateBox createdTo;
	@UiField
	Button buttonSearch;

	public SearchArticleForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getButtonSearch() {
		return buttonSearch;
	}

	public String getArticleTitle() {
		return title.getText();
	}

	public String getFirstName() {
		return firstName.getText();
	}

	public String getLastName() {
		return lastName.getText();
	}

	public Date getCreatedFrom() {
		return createdFrom.getValue();
	}

	public Date getCreatedTo() {
		return createdTo.getValue();
	}

}
