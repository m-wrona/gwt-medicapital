package com.medicapital.client.ui.url;

import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.url.UrlResourceListPresenter;
import com.medicapital.client.core.url.UrlResourceListView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.PagingForm;
import com.medicapital.common.entities.UrlResource;

/**
 * Class displays URL resources in slider form
 * 
 * @author mwronski
 * 
 */
public class UrlResourceSliderForm extends PopupableComposite implements UrlResourceListView {

	private static PhotoSliderFormUiBinder uiBinder = GWT.create(PhotoSliderFormUiBinder.class);

	interface PhotoSliderFormUiBinder extends UiBinder<Widget, UrlResourceSliderForm> {
	}

	@UiField
	PagingForm pagingForm;
	@UiField
	HorizontalPanel urlResourcePanel;
	private UrlResourceListPresenter<?, ?> urlResourceListPresenter;

	public UrlResourceSliderForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	@Override
	public void clear() {
		urlResourcePanel.clear();
	}

	@Override
	public void addResources(UrlResource urlResource) {
		Image photo = new Image(urlResource.getUrl());
		photo.setTitle(urlResource.getDescription());
		urlResourcePanel.add(photo);
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

	private void handleGoToPage(ChangeEvent event) {
		if (urlResourceListPresenter != null) {
			urlResourceListPresenter.goToPage(pagingForm.getCurrentPage());
		}
	}

	private void handleFirstClick(ClickEvent event) {
		if (urlResourceListPresenter != null) {
			urlResourceListPresenter.goToFirstPage();
		}
	}

	private void handleNextClick(ClickEvent event) {
		if (urlResourceListPresenter != null) {
			urlResourceListPresenter.goToNextPage();
		}
	}

	private void handlePreviousClick(ClickEvent event) {
		if (urlResourceListPresenter != null) {
			urlResourceListPresenter.goToPreviousPage();
		}
	}

	private void handleLastClick(ClickEvent event) {
		if (urlResourceListPresenter != null) {
			urlResourceListPresenter.goToLastPage();
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
	public void setUrlListPresenter(UrlResourceListPresenter<?, ?> urlResourceListPresenter) {
		this.urlResourceListPresenter = urlResourceListPresenter;
	}

}
