package com.medicapital.client.doctor;

import com.medicapital.client.calendar.CalendarCommandFactory;
import com.medicapital.client.calendar.CalendarEventDataDisplayer;
import com.medicapital.client.calendar.CalendarPresenter;
import com.medicapital.client.calendar.CalendarView;
import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.validation.DoctorValidator;
import com.medicapital.common.validation.DoctorValidatorView;

final public class DoctorFactory {

	private final EntityCommandFactory<Doctor> commandFactory = new EntityCommandFactory<Doctor>(Doctor.class);

	public DoctorPresenter createDoctorPresenter(final DoctorView doctorView) {
		DoctorViewBinder viewBinder = new DoctorViewBinder(doctorView, null);
		return new DoctorPresenter(doctorView, viewBinder, DaoFactory.getEventBus(), DaoFactory.getDoctorService(), DaoFactory.getDoctorUrlService());
	}

	public SearchDoctorsPresenter createDoctorListPresenter(final SearchDoctorsView doctorsView, final DoctorView doctorView) {
		return new SearchDoctorsPresenter(doctorsView, DaoFactory.getEventBus(), DaoFactory.getDoctorService(), createDoctorPresenter(doctorView));
	}

	public CalendarPresenter createCalendarPresenter(final CalendarView calendarView) {
		CalendarEventDataDisplayer eventDataDisplayer = new CalendarEventDataDisplayer();
		final CalendarPresenter calendarPresenter = new CalendarPresenter(calendarView, DaoFactory.getEventBus(), eventDataDisplayer);
		calendarPresenter.setServiceAccess(DaoFactory.getServiceAccess());
		calendarPresenter.setCommandFactory(new CalendarCommandFactory());
		calendarView.setCalendarPresenter(calendarPresenter);
		return calendarPresenter;
	}

	public EditDoctorPresenter createEditDoctorPresenter(final EditDoctorView editDoctorView, DoctorValidatorView validatorView) {
		DoctorViewBinder viewBinder = new DoctorViewBinder(editDoctorView, editDoctorView);
		DoctorValidator doctorValidator = new DoctorValidator();
		doctorValidator.setView(validatorView);
		final EditDoctorPresenter editDoctorPresenter = new EditDoctorPresenter(editDoctorView, viewBinder, DaoFactory.getEventBus(), DaoFactory.getDoctorService(), DaoFactory.getDoctorUrlService());
		editDoctorPresenter.setEntityValidator(doctorValidator);
		return editDoctorPresenter;
	}

	public CreateDoctorPresenter createCreateDoctorPresenter(final CreateDoctorView view, DoctorValidatorView validatorView) {
		DoctorViewBinder viewBinder = new DoctorViewBinder(view, view);
		DoctorValidator doctorValidator = new DoctorValidator();
		doctorValidator.setView(validatorView);
		CreateDoctorPresenter createDoctorPresenter = new CreateDoctorPresenter(view, viewBinder, DaoFactory.getEventBus(), DaoFactory.getDoctorService(), DaoFactory.getDoctorUrlService());
		createDoctorPresenter.setEntityValidator(doctorValidator);
		return createDoctorPresenter;
	}

	public DoctorGalleryPresenter createGalleryPresenter(DoctorGalleryView galleryView) {
		DoctorGalleryPresenter galleryPresenter = new DoctorGalleryPresenter(galleryView, DaoFactory.getDoctorUrlService());
		galleryView.setGalleryPresenter(galleryPresenter);
		return galleryPresenter;
	}

	public EntityCommandFactory<Doctor> getCommandFactory() {
		return commandFactory;
	}
}
