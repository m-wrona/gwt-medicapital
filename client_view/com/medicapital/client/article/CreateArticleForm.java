package com.medicapital.client.article;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;

public class CreateArticleForm extends EditArticleDataForm implements CreateArticleView {

	private CreateArticlePresenter createArticlePresenter;
	// TODO timer should be move to presenter
	private Timer autosaveTimer;

	public CreateArticleForm() {
		buttonDelete.setVisible(false);
		initButtonHandlers();
		autosaveTimer = new Timer() {

			@Override
			public void run() {
				if (createArticlePresenter != null) {
					createArticlePresenter.saveChanges();
				}
			}
		};
		autosaveTimer.scheduleRepeating(SystemSettings.getAutoSaveTime());
	}

	private void initButtonHandlers() {
		buttonSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createArticlePresenter != null) {
					createArticlePresenter.create();
				}

			}
		});
		buttonCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createArticlePresenter != null) {
					createArticlePresenter.cancel();
				}

			}
		});
	}

	@Override
	public EditUrlResourceListPresenterView getAttachmentView() {
		return attachmentsForm;
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonSave.setEnabled(enabled);
	}

	@Override
	public void setCreatePresenter(CreateArticlePresenter createArticlePresenter) {
		this.createArticlePresenter = createArticlePresenter;
	}

}
