package com.medicapital.common.validation;

public interface UserValidatorView {

	void setLoginEmptyMsgVisible(boolean visible);

	void setWrongEmailMsgVisible(boolean visible);

	void setFirstNameEmptyMsgVisible(boolean visible);

	void setLastNameEmptyMsgVisible(boolean visible);

	boolean validateOptionalData();

}