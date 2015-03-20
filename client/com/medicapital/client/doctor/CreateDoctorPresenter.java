package com.medicapital.client.doctor;

import static com.medicapital.client.log.Tracer.tracer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.mvp.CreateEntityPresenter;
import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.client.user.CreateUserPresenter;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.common.dao.entity.DoctorServiceAsync;
import com.medicapital.common.entities.Doctor;

public final class CreateDoctorPresenter extends CreateEntityPresenter<Doctor> {

	private final CreateDoctorView view;
	private final DoctorServiceAsync doctorService;
	private final DoctorUrlService imageService;

	public CreateDoctorPresenter(CreateDoctorView view, EntityViewBinder<Doctor> viewBinder, EventBus eventBus, DoctorServiceAsync doctorService, DoctorUrlService doctorUrlService) {
		super(Doctor.class, view, viewBinder, eventBus, doctorService);
		this.view = view;
		this.doctorService = doctorService;
		this.imageService = doctorUrlService;
		initHandlers();
	}
	
	private void initHandlers() {
		view.getIsLoginFreeClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				checkIsLoginFree();
			}
		});
	}

	/**
	 * Check if given login is not used in service yet.
	 * 
	 */
	public final void checkIsLoginFree() {
		final String login = view.getLogin();
		tracer(CreateUserPresenter.class).debug("Checking if login is free, login: " + login);
		view.setLoginFreeMessageVisible(false);
		view.setLoginExistMessageVisible(false);
		view.setLoginEmptyMessageVisible(false);
		if (login == null || login.isEmpty()) {
			view.setLoginEmptyMessageVisible(true);
			return;
		}
		doctorService.isLoginFree(login, new ServerCallback<Boolean>() {
			@Override
			public void response(Boolean loginFree) {
				if (loginFree) {
					view.setLoginFreeMessageVisible(true);
				} else {
					view.setLoginExistMessageVisible(true);
				}
			}
		});
	}

	@Override
	protected final void displayEntityOnView(Doctor entity) {
		super.displayEntityOnView(entity);
		if (imageService != null) {
			tracer(this).debug("Getting profile photo from image service for doctor: " + entity.getId());
			view.setDoctorProfileImage(imageService.getDoctorProfileImageURL(entity.getId()));
		}
	}

}
