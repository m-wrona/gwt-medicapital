package com.medicapital.common.validation;

import static com.medicapital.common.validation.ValidationUtils.*;
import com.medicapital.common.entities.User;

final public class UserValidator implements EntityValidator<User> {

	private UserValidatorView view;

	private void clearValidationMessages() {
		if (view != null) {
			view.setLoginEmptyMsgVisible(false);
			view.setWrongEmailMsgVisible(false);
			view.setFirstNameEmptyMsgVisible(false);
			view.setLastNameEmptyMsgVisible(false);
		}
	}

	@Override
	final public boolean validate(User entity) {
		clearValidationMessages();
		boolean valid = true;
		if (isEmpty(entity.getLogin())) {
			valid = false;
			if (view != null) {
				view.setLoginEmptyMsgVisible(true);
			}
		}
		if (!isEmailValid(entity.getEmail())) {
			valid = false;
			if (view != null) {
				view.setWrongEmailMsgVisible(true);
			}
		}
		if (isEmpty(entity.getFirstName())) {
			valid = false;
			if (view != null) {
				view.setFirstNameEmptyMsgVisible(true);
			}
		}
		if (isEmpty(entity.getLastName())) {
			valid = false;
			if (view != null) {
				view.setLastNameEmptyMsgVisible(true);
			}
		}
		if (view != null && !view.validateOptionalData()) {
			valid = false;
		}
		return valid;
	}

	final public void setView(UserValidatorView view) {
		this.view = view;
	}

	final public UserValidatorView getView() {
		return view;
	}

}
