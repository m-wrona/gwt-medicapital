package com.medicapital.client.core.url;

import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.upload.FileServerUploadView;
import com.medicapital.common.entities.UrlResource;

public interface EditUrlResourcePresenterView extends WidgetView, FileServerUploadView {

	void display(UrlResource urlResource);

	void setEditUrlResourceListPresenter(EditUrlResourcePresenter editUrlResourceListPresenter);
}
