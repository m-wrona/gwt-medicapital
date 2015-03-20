package com.medicapital.client.article;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.url.EditUrlResourcePresenter;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.text.RichTextAreaForm;
import com.medicapital.client.ui.url.EditUrlResourceForm;
import com.medicapital.client.ui.url.EditUrlResourceSliderForm;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.entities.UrlResource;

class EditArticleDataForm extends PopupableComposite implements SetterArticleView, GetterArticleView {

	private static ArticleFormUiBinder uiBinder = GWT.create(ArticleFormUiBinder.class);

	interface ArticleFormUiBinder extends UiBinder<Widget, EditArticleDataForm> {
	}

	@UiField
	TextBox title;
	@UiField
	RichTextAreaForm body;
	@UiField
	Button buttonSave;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonCancel;
	@UiField
	Label created;
	@UiField
	Label updated;
	@UiField
	Label firstName;
	@UiField
	Label lastName;
	@UiField
	EditUrlResourceForm mainPhoto;
	@UiField
	CheckBox published;
	@UiField
	EditUrlResourceSliderForm attachmentsForm;

	private EditUrlResourcePresenter editUrlResourcePresenter;

	public EditArticleDataForm() {
		initWidget(uiBinder.createAndBindUi(this));
		attachmentsForm.getChooseFile().setDescriptionVisible(true);
		initButtonHandlers();
		setButtonCloseWindow(buttonCancel);
	}

	private void initButtonHandlers() {
		mainPhoto.getButtonSave().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editUrlResourcePresenter != null) {
					editUrlResourcePresenter.upload();
				}
			}
		});
		mainPhoto.getButtonDelete().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editUrlResourcePresenter != null) {
					editUrlResourcePresenter.delete();
				}
			}
		});
	}

	@Override
	public String getArticleTitle() {
		return title.getText();
	}

	@Override
	public void setEditUrlResourceListPresenter(EditUrlResourcePresenter editUrlResourceListPresenter) {
		this.editUrlResourcePresenter = editUrlResourceListPresenter;
	}

	@Override
	public FormPanel getFormPanel() {
		return mainPhoto.getFormPanel();
	}

	@Override
	public FileUpload getFileUpload() {
		return mainPhoto.getFileUpload();
	}

	@Override
	public UrlResource getUrlResource() {
		return mainPhoto.getUrlResource();
	}

	@Override
	public WidgetView getFormPanelParent() {
		return mainPhoto.getFormPanelParent();
	}

	@Override
	public String getAtricleBody() {
		return body.getRichText().getText();
	}

	@Override
	public boolean isPublished() {
		return published.getValue();
	}

	@Override
	public void setArticleTitle(String text) {
		title.setValue(text);
	}

	@Override
	public void setArticleBody(String text) {
		body.getRichText().setText(text);
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
	public void setCreateDate(Date date) {
		created.setText(DateFactory.createDateFormatter().toDateAndTimeString(date));

	}

	@Override
	public void setLastUpdateDate(Date date) {
		updated.setText(DateFactory.createDateFormatter().toDateAndTimeString(date));
	}

	@Override
	public void setPublished(boolean published) {
		this.published.setValue(published);
	}

	@Override
	public void display(UrlResource urlResource) {
		mainPhoto.setUrlResource(urlResource);
	}
	
	public Button getButtonCancel() {
	    return buttonCancel;
    }

}
