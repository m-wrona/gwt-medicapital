package com.medicapital.client.doctor;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.mvp.EditEntityPresenter;
import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.common.dao.entity.DoctorServiceAsync;
import com.medicapital.common.entities.Doctor;

public final class EditDoctorPresenter extends EditEntityPresenter<Doctor> {

	private final EditDoctorView display;
	private DoctorUrlService imageService;

	public EditDoctorPresenter(EditDoctorView display, EntityViewBinder<Doctor> viewBinder, EventBus eventBus, DoctorServiceAsync doctorService, DoctorUrlService doctorUrlService) {
		super(Doctor.class, display, viewBinder, eventBus, doctorService);
		this.display = display;
		this.imageService = doctorUrlService;
	}

	@Override
	protected void displayEntityOnView(Doctor entity) {
		super.displayEntityOnView(entity);
		if (imageService != null) {
			tracer(this).debug("Getting profile photo from image service for doctor: " + entity.getId());
			display.setDoctorProfileImage(imageService.getDoctorProfileImageURL(entity.getId()));
		}
	}

}
