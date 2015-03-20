package com.medicapital.client.doctor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.core.mvp.CreateEntityPresenterView;
import com.medicapital.client.user.CreateUserValidationView;
import com.medicapital.common.entities.Doctor;

public interface CreateDoctorView extends CreateEntityPresenterView<Doctor>, SetterDoctorDataView, GetterDoctorDataView, CreateUserValidationView {

	HasClickHandlers getIsLoginFreeClickHandler();

}
