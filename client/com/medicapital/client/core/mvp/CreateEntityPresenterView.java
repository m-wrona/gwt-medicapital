package com.medicapital.client.core.mvp;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.common.entities.SerializableEntity;

public interface CreateEntityPresenterView<E extends SerializableEntity> extends EntityPresenterView<E> {

	HasClickHandlers getCreateClickHandler();

	HasClickHandlers getCancelClickHandler();

	void setEntityCreatedMsgVisible(boolean visible);

	void setEntityNotCreatedMsgVisible(boolean visible);

	void setCreateButtonEnabled(boolean enabled);

}
