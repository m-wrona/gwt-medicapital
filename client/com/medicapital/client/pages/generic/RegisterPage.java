package com.medicapital.client.pages.generic;

import static com.medicapital.client.log.Tracer.tracer;
import com.medicapital.client.doctor.CreateDoctorPresenter;
import com.medicapital.client.doctor.DoctorFactory;
import com.medicapital.client.pages.Page;
import com.medicapital.client.user.CreateUserForm;
import com.medicapital.client.user.CreateUserPresenter;
import com.medicapital.client.user.UserFactory;

final public class RegisterPage extends Page<RegisterPageForm> {

	public static final String PAGE_NAME = "Register";
	private CreateUserPresenter registerUserPresenter;
	private CreateDoctorPresenter registerDoctorPresenter;

	@Override
	protected RegisterPageForm createPageView() {
		return new RegisterPageForm();
	}

	@Override
	protected void initPage(RegisterPageForm pageView) {
		registerUser();
	}

	final public void registerUser() {
		tracer(this).debug("Showing register user view...");
		final UserFactory userFactory = new UserFactory();
		CreateUserForm userView = getPageView().getUserView();
		registerUserPresenter = userFactory.createCreateUserPresenter(userView, userView);
		getPageView().getPagePanel().clear();
		getPageView().getPagePanel().add(registerUserPresenter.getEntityPresenterView().asWidget());
		getPresenters().add(registerUserPresenter);
	}

	final public void registerDoctor() {
		tracer(this).debug("Showing register doctor view...");
		final DoctorFactory doctorFactory = new DoctorFactory();
		registerDoctorPresenter = doctorFactory.createCreateDoctorPresenter(getPageView().getDoctorView(), getPageView().getDoctorView());
		getPageView().getPagePanel().clear();
		getPageView().getPagePanel().add(registerDoctorPresenter.getEntityPresenterView().asWidget());
		getPresenters().add(registerDoctorPresenter);
	}

}
