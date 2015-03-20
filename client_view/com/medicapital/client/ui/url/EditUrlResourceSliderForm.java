package com.medicapital.client.ui.url;

import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.core.url.EditUrlResourceListPresenter;
import com.medicapital.client.core.url.EditUrlResourceListPresenterView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.PagingForm;
import com.medicapital.common.entities.UrlResource;

public class EditUrlResourceSliderForm extends PopupableComposite implements EditUrlResourceListPresenterView {

	private static EditUrlResourceSliderFormUiBinder uiBinder = GWT.create(EditUrlResourceSliderFormUiBinder.class);

	interface EditUrlResourceSliderFormUiBinder extends UiBinder<Widget, EditUrlResourceSliderForm> {
	}

	@UiField
	PagingForm pagingForm;
	@UiField
	HorizontalPanel urlResourcePanel;
	@UiField
	Button buttonSave;
	@UiField
	Button buttonDelete;
	private final ChooseUrlResource chooseFile = new ChooseUrlResource();
	private EditUrlResourceListPresenter<?, ?> editUrlResourcePresenter;
	private UrlResource selectedUrlResource;

	public EditUrlResourceSliderForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
		initFileActionHandlers();
		buttonDelete.setEnabled(false);
		// chooseFile.getButtonDelete().setVisible(false);
	}

	@Override
	public void clear() {
		urlResourcePanel.clear();
		buttonDelete.setEnabled(false);
	}

	@Override
	public void addResources(final UrlResource urlResource) {
		Image photo = new Image(urlResource.getUrl());
		photo.setTitle(urlResource.getDescription());
		urlResourcePanel.add(photo);
		photo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonDelete.setEnabled(true);
				selectedUrlResource = urlResource;
			}
		});
	}

	@Override
	public void addResource(Set<UrlResource> urlResources) {
		urlResourcePanel.clear();
		if (urlResources != null) {
			for (UrlResource urlResource : urlResources) {
				addResources(urlResource);
			}
		}
	}

	private void initHandlers() {
		pagingForm.getButtonFirst().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleFirstClick(event);

			}
		});
		pagingForm.getButtonNext().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleNextClick(event);

			}
		});
		pagingForm.getButtonPrevious().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handlePreviousClick(event);

			}
		});
		pagingForm.getButtonLast().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleLastClick(event);

			}
		});
		pagingForm.getCurrentPageNumber().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				handleGoToPage(event);
			}
		});

	}

	private void initFileActionHandlers() {
		buttonSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				chooseFile.setViewVisible(true);
			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editUrlResourcePresenter != null && selectedUrlResource != null) {
					editUrlResourcePresenter.delete(selectedUrlResource);
				}
			}
		});
		chooseFile.getButtonSave().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editUrlResourcePresenter != null) {
					editUrlResourcePresenter.upload();
				}
			}
		});
	}

	private void handleGoToPage(ChangeEvent event) {
		if (editUrlResourcePresenter != null) {
			editUrlResourcePresenter.goToPage(pagingForm.getCurrentPage());
		}
	}

	private void handleFirstClick(ClickEvent event) {
		if (editUrlResourcePresenter != null) {
			editUrlResourcePresenter.goToFirstPage();
		}
	}

	private void handleNextClick(ClickEvent event) {
		if (editUrlResourcePresenter != null) {
			editUrlResourcePresenter.goToNextPage();
		}
	}

	private void handlePreviousClick(ClickEvent event) {
		if (editUrlResourcePresenter != null) {
			editUrlResourcePresenter.goToPreviousPage();
		}
	}

	private void handleLastClick(ClickEvent event) {
		if (editUrlResourcePresenter != null) {
			editUrlResourcePresenter.goToLastPage();
		}
	}

	@Override
	public void setNextEnable(boolean enabled) {
		pagingForm.setNextEnable(enabled);
	}

	@Override
	public void setPreviousEnable(boolean enabled) {
		pagingForm.setPreviousEnable(enabled);
	}

	@Override
	public void setFirstEnable(boolean enabled) {
		pagingForm.setFirstEnable(enabled);
	}

	@Override
	public void setLastEnable(boolean enabled) {
		pagingForm.setLastEnable(enabled);
	}

	@Override
	public void setCurrentPageNumber(int currentPageNumber) {
		pagingForm.setCurrentPageNumber(currentPageNumber);
	}

	@Override
	public void setTotalPageNumber(int totalPageNumber) {
		pagingForm.setTotalPageNumber(totalPageNumber);
	}

	@Override
	public void setEditUrlResourceListPresenter(EditUrlResourceListPresenter<?, ?> editUrlResourceListPresenter) {
		this.editUrlResourcePresenter = editUrlResourceListPresenter;
	}

	@Override
	public FileUpload getFileUpload() {
		return chooseFile.getFileUpload();
	}

	@Override
	public FormPanel getFormPanel() {
		return chooseFile.getFormPanel();
	}

	@Override
	public UrlResource getUrlResource() {
		return chooseFile.getUrlResource();
	}

	@Override
	public WidgetView getFormPanelParent() {
		return chooseFile;
	}
	
	public ChooseUrlResource getChooseFile() {
	    return chooseFile;
    }

}
