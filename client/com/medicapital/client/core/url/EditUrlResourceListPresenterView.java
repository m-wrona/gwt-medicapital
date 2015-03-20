package com.medicapital.client.core.url;

import com.medicapital.client.core.upload.FileServerUploadView;

public interface EditUrlResourceListPresenterView extends BasicUrlResourceListView, FileServerUploadView {

	void setEditUrlResourceListPresenter(EditUrlResourceListPresenter<?, ?> editUrlResourceListPresenter);

}
