package com.medicapital.client.pages.user;

import static com.medicapital.client.log.Tracer.tracer;
import com.medicapital.client.core.mvp.EditEntityPresenter;
import com.medicapital.client.doctor.DoctorFactory;
import com.medicapital.client.doctor.EditDoctorPresenter;
import com.medicapital.client.pages.UserPage;
import com.medicapital.client.user.UserFactory;
import com.medicapital.common.entities.User;
import com.medicapital.common.validation.UserValidator;

final public class UserProfilePage extends UserPage<UserProfilePageForm> {

	private EditEntityPresenter<User> editUserPresenter;
	private EditDoctorPresenter editDoctorPresenter;

	@Override
	protected UserProfilePageForm createPageView() {
		return new UserProfilePageForm();
	}

	@Override
	protected void initPage(UserProfilePageForm pageView) {
		tracer(this).debug("Displaying user profile - login data: " + getLoginData());
		pageView.getDataPanel().clear();
		if (getLoginData() != null) {
			switch (getLoginData().getRole()) {
			case User:
				final UserFactory userFactory = new UserFactory();
				userFactory.getCommandFactory().setLogin(getLoggedUser());
				editUserPresenter = userFactory.createEditUserPresenter(pageView.getEditUserForm(), pageView.getEditUserForm());
				UserValidator userValidator = new UserValidator();
				userValidator.setView(pageView.getEditUserForm());
				editUserPresenter.setEntityValidator(userValidator);
				pageView.getDataPanel().add(editUserPresenter.getEntityPresenterView().asWidget());
				editUserPresenter.display(getLoggedUserId());
				getPresenters().add(editUserPresenter);
				break;

			case Doctor:
				final DoctorFactory doctorFactory = new DoctorFactory();
				doctorFactory.getCommandFactory().setLogin(getLoggedUser());
				editDoctorPresenter = doctorFactory.createEditDoctorPresenter(pageView.getEditDoctorForm(), pageView.getEditDoctorForm());
				pageView.getDataPanel().add(editDoctorPresenter.getEntityPresenterView().asWidget());
				editDoctorPresenter.display(getLoggedUserId());
				getPresenters().add(editDoctorPresenter);
				break;
			}
		}
	}

}
