package com.medicapital.common.validation;

import com.medicapital.common.entities.Doctor;

public class DoctorValidator implements EntityValidator<Doctor> {

	private DoctorValidatorView doctorValidatorView;
	
	@Override
    public boolean validate(Doctor entity) {
	    UserValidator userValidator = new UserValidator();
	    userValidator.setView(doctorValidatorView);
	    return userValidator.validate(entity.getUser());
    }
	
	public void setView(DoctorValidatorView doctorValidatorView) {
	    this.doctorValidatorView = doctorValidatorView;
    }

}
