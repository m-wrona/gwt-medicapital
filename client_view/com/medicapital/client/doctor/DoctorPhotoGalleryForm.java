package com.medicapital.client.doctor;

import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.DoctorGalleryPresenter;
import com.medicapital.client.doctor.DoctorGalleryView;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.PagingForm;

public class DoctorPhotoGalleryForm extends PopupableComposite implements DoctorGalleryView {

	private static DoctorPhotoGalleryFormUiBinder uiBinder = GWT.create(DoctorPhotoGalleryFormUiBinder.class);

	interface DoctorPhotoGalleryFormUiBinder extends UiBinder<Widget, DoctorPhotoGalleryForm> {
	}

	@UiField
	PagingForm pagingForm;
	private DoctorGalleryPresenter doctorGalleryPresenter;

	public DoctorPhotoGalleryForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	@Override
	public void displayGalleryPhotos(Set<String> galleryPhotoUrls) {
		// TODO Auto-generated method stub

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
		if (doctorGalleryPresenter != null) {
			doctorGalleryPresenter.goToPage(pagingForm.getCurrentPage());
		}
	}

	private void handleFirstClick(ClickEvent event) {
		if (doctorGalleryPresenter != null) {
			doctorGalleryPresenter.goToFirstPage();
		}
	}

	private void handleNextClick(ClickEvent event) {
		if (doctorGalleryPresenter != null) {
			doctorGalleryPresenter.goToNextPage();
		}
	}

	private void handlePreviousClick(ClickEvent event) {
		if (doctorGalleryPresenter != null) {
			doctorGalleryPresenter.goToPreviousPage();
		}
	}

	private void handleLastClick(ClickEvent event) {
		if (doctorGalleryPresenter != null) {
			doctorGalleryPresenter.goToLastPage();
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
	public void setGalleryPresenter(DoctorGalleryPresenter doctorGalleryPresenter) {
		this.doctorGalleryPresenter = doctorGalleryPresenter;
	}

}
