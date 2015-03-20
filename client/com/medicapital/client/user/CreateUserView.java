package com.medicapital.client.user;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.client.core.mvp.CreateEntityPresenterView;
import com.medicapital.common.entities.User;

public interface CreateUserView extends CreateEntityPresenterView<User>, SetterUserDataView, GetterUserDataView, CreateUserValidationView {

	HasClickHandlers getIsLoginFreeClickHandler();

}
