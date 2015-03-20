package com.medicapital.client.core.mvp;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.common.entities.SerializableEntity;

public interface EditEntityPresenterView<E extends SerializableEntity> extends EntityPresenterView<E> {

	HasClickHandlers getUpdateClickHandler();

	HasClickHandlers getDeleteClickHandler();

	HasClickHandlers getCancelClickHandler();

	void setEntityUpdatedMsgVisible(boolean visible);

	void setEntityNotUpdatedMsgVisible(boolean visible);

	void setEntityDeletedMsgVisible(boolean visible);

	void setEntityNotDeletedMsgVisible(boolean visible);

	void setUpdateButtonEnabled(boolean enabled);

	void setDeleteButtonEnabled(boolean enabled);

}
