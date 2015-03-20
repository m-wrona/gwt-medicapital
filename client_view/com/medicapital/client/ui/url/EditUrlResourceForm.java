package com.medicapital.client.ui.url;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.upload.FileServerUploadView;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.log.Tracer;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.img.Images;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to edit URL resource
 * 
 * @author mwronski
 * 
 */
public class EditUrlResourceForm extends PopupableComposite implements FileServerUploadView {

	private static EditPhotoFormUiBinder uiBinder = GWT.create(EditPhotoFormUiBinder.class);

	interface EditPhotoFormUiBinder extends UiBinder<Widget, EditUrlResourceForm> {
	}

	@UiField
	Image urlResource;

	private final ChooseUrlResource chooseFile = new ChooseUrlResource();

	public EditUrlResourceForm() {
		initWidget(uiBinder.createAndBindUi(this));
		urlResource.setResource(Images.instance.imageNotSet());
		initHandlers();
		chooseFile.getButtonDelete().setVisible(false);
		urlResource.setTitle(Lang.generic().clickToSetImage());
	}

	private void initHandlers() {
		urlResource.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				chooseFile.setViewVisible(true);
			}
		});
	}

	public void setUrlResource(UrlResource resource) {
		Tracer.tracer(this).debug("Set URL resource: " + resource);
		if (resource == null || resource.isUrlEmpty()) {
			urlResource.setResource(Images.instance.imageNotSet());
			chooseFile.getButtonDelete().setVisible(false);
		} else {
			urlResource.setUrl(resource.getUrl());
			chooseFile.getButtonDelete().setVisible(true);
		}
		urlResource.setTitle(Lang.generic().clickToSetImage());
	}

	@Override
	public UrlResource getUrlResource() {
		return chooseFile.getUrlResource();
	}

	public Button getButtonSave() {
		return chooseFile.getButtonSave();
	}

	public Button getButtonDelete() {
		return chooseFile.getButtonDelete();
	}

	@Override
	public FormPanel getFormPanel() {
		return chooseFile.getFormPanel();
	}

	@Override
	public FileUpload getFileUpload() {
		return chooseFile.getFileUpload();
	}

	@Override
	public WidgetView getFormPanelParent() {
		return chooseFile;
	}

}
