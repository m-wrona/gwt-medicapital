package com.medicapital.client.article;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.url.UrlResourceListView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.url.UrlResourceSliderForm;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.entities.UrlResource;

public class DisplayArticleForm extends PopupableComposite implements ArticleView {

	private static DisplayArticleFormUiBinder uiBinder = GWT.create(DisplayArticleFormUiBinder.class);

	interface DisplayArticleFormUiBinder extends UiBinder<Widget, DisplayArticleForm> {
	}

	@UiField
	Label title;
	@UiField
	HTML body;
	@UiField
	Label created;
	@UiField
	Label updated;
	@UiField
	Label firstName;
	@UiField
	Label lastName;
	@UiField
	Image mainPhoto;
	@UiField
	UrlResourceSliderForm photos;

	public DisplayArticleForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void display(UrlResource urlResource) {
		if (urlResource != null) {
			mainPhoto.setUrl(urlResource.getUrl());
		} else {
			mainPhoto.setUrl(null);
		}
	}

	@Override
	public UrlResourceListView getPhotoListView() {
		return photos;
	}

	@Override
	public void setCreateDate(Date date) {
		created.setText(DateFactory.createDateFormatter().toDateAndTimeString(date));
	}

	@Override
	public void setLastUpdateDate(Date date) {
		updated.setText(DateFactory.createDateFormatter().toDateAndTimeString(date));
	}

	@Override
	public void setArticleTitle(String text) {
		title.setText(text);
	}

	@Override
	public void setArticleBody(String text) {
		body.setText(text);
	}

	@Override
	public void setAuthorFirstName(String firstName) {
		this.firstName.setText(firstName);
	}

	@Override
	public void setAuthorLastName(String lastName) {
		this.lastName.setText(lastName);
	}

	@Override
	public void setPublished(boolean published) {
		// ignore
	}

}
