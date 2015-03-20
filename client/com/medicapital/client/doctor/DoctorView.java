package com.medicapital.client.doctor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.core.mvp.EntityPresenterView;
import com.medicapital.common.entities.Doctor;

public interface DoctorView extends SetterDoctorDataView, EntityPresenterView<Doctor> {

	HasClickHandlers getGoToHomePageClickHandler();

	HasClickHandlers getGoToCalendarClickHandler();
}
