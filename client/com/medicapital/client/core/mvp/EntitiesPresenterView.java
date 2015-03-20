package com.medicapital.client.core.mvp;

import com.medicapital.client.core.PageableView;
import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.SerializableEntity;

public interface EntitiesPresenterView<E extends SerializableEntity> extends PageableView, WidgetView {

	/**
	 * Clear the view
	 */
	void clear();
}
