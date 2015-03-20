package com.medicapital.client.ui.url;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.upload.FileServerUploadView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.UrlResourceType;

/**
 * Class allows to choose URL resource which should be uploaded to the server
 * 
 * @author mwronski
 * 
 */
public class ChooseUrlResource extends PopupableComposite implements FileServerUploadView {

	private static ChooseFileUiBinder uiBinder = GWT.create(ChooseFileUiBinder.class);

	interface ChooseFileUiBinder extends UiBinder<Widget, ChooseUrlResource> {
	}

	@UiField
	FormPanel formPanel;
	@UiField
	FileUpload fileUpload;
	@UiField
	Label labelDescription;
	@UiField
	TextBox description;
	@UiField
	Button buttonSave;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonCancel;

	public ChooseUrlResource() {
		initWidget(uiBinder.createAndBindUi(this));
		setButtonCloseWindow(buttonCancel);
		setShowAsDialogBox(true);
	}

	@Override
	public FormPanel getFormPanel() {
		return formPanel;
	}

	@Override
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	@Override
	public UrlResource getUrlResource() {
		UrlResource urlResource = new UrlResource();
		urlResource.setDescription(description.getValue());
		urlResource.setUrlResourceType(UrlResourceType.getFileType(fileUpload.getFilename()));
		return urlResource;
	}

	public Button getButtonSave() {
		return buttonSave;
	}

	public Button getButtonDelete() {
		return buttonDelete;
	}

	@Override
	public WidgetView getFormPanelParent() {
		return this;
	}

	public void setDescriptionVisible(boolean visible) {
		labelDescription.setVisible(visible);
		description.setVisible(visible);
	}

}
