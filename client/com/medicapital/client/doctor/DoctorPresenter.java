package com.medicapital.client.doctor;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.mvp.EntityPresenter;
import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.client.pages.doctor.DoctorCalendarPage;
import com.medicapital.client.pages.doctor.DoctorHomePage;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.common.dao.entity.DoctorServiceAsync;
import com.medicapital.common.entities.Doctor;

public final class DoctorPresenter extends EntityPresenter<Doctor> {

	private final DoctorView view;
	private final DoctorUrlService imageService;

	public DoctorPresenter(DoctorView view, EntityViewBinder<Doctor> viewBinder, EventBus eventBus, DoctorServiceAsync doctorService, DoctorUrlService doctorUrlService) {
		super(Doctor.class, view, viewBinder, eventBus, doctorService);
		this.view = view;
		this.imageService = doctorUrlService;
		initHandlers();
	}

	private void initHandlers() {
		if (view.getGoToCalendarClickHandler() != null) {
			view.getGoToCalendarClickHandler().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					displayCalendar();
				}
			});
		}
		if (view.getGoToHomePageClickHandler() != null) {
			view.getGoToHomePageClickHandler().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					displayDoctorHomePage();
				}
			});
		}
	}

	@Override
	protected void displayEntityOnView(Doctor entity) {
		super.displayEntityOnView(entity);
		if (imageService != null) {
			tracer(this).debug("Getting profile photo from image service for doctor: " + entity.getId());
			view.setDoctorProfileImage(imageService.getDoctorProfileImageURL(entity.getId()));
		}
	}

	@Override
	protected void clearView() {
		super.clearView();
		view.setDoctorProfileImage(null);
	}

	/**
	 * Display calendar of current displayed doctor
	 * 
	 * @throws IllegalArgumentException
	 *             when no doctor is displayed in presenter.
	 */
	public void displayCalendar() throws IllegalArgumentException {
		if (getCurrentEntity() == null) {
			throw new IllegalArgumentException("No doctor is displayed");
		}
		tracer(this).debug("Displaying calendar of doctor: " + getCurrentEntity().getId());
		getEventBus().fireEvent(DoctorCalendarPage.createPageEvent(this, getCurrentEntity().getId()));
		view.setViewVisible(false);
	}

	/**
	 * Display home page of current displayed doctor
	 * 
	 * @throws IllegalArgumentException
	 */
	public void displayDoctorHomePage() throws IllegalArgumentException {
		if (getCurrentEntity() == null) {
			throw new IllegalArgumentException("No doctor is displayed");
		}
		tracer(this).debug("Displaying doctor home page: " + getCurrentEntity().getId());
		getEventBus().fireEvent(DoctorHomePage.createPageEvent(this, getCurrentEntity().getId()));
		view.setViewVisible(false);
	}

}
