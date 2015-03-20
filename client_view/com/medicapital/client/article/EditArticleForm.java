package com.medicapital.client.article;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;

public class EditArticleForm extends EditArticleDataForm implements EditArticleView {

	private EditArticlePresenter editArticlePresenter;

	public EditArticleForm() {
		initButtonHandlers();
	}

	private void initButtonHandlers() {
		buttonSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editArticlePresenter != null) {
					editArticlePresenter.update();
				}

			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editArticlePresenter != null) {
					editArticlePresenter.delete();
				}

			}
		});
	}

	@Override
	public void setDeleteHandlerEnabled(boolean enabled) {
		buttonDelete.setEnabled(enabled);
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonSave.setEnabled(enabled);
	}

	@Override
	public void setEditPresenter(EditArticlePresenter editArticlePresenter) {
		this.editArticlePresenter = editArticlePresenter;
	}

	@Override
	public EditUrlResourceListPresenterView getAttachmentView() {
		return attachmentsForm;
	}

}
