package com.medicapital.client.core.mvp;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.medicapital.common.entities.SerializableEntity;

public interface SearchEntityPresenterView<E extends SerializableEntity> extends EntitiesPresenterView<E> {

	HasClickHandlers getSearchClickHandler();

	HasClickHandlers getCancelClickHandler();

	HasClickHandlers getDisplayDetailClickHandler(int entityId);
}
