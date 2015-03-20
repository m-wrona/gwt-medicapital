package com.medicapital.client.core.entities;

import com.medicapital.client.core.PageableView;
import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface allows to display many elements
 * 
 * @author michal
 * 
 */
public interface EntitiesView<E extends SerializableEntity> extends PageableView, WidgetView {

	/**
	 * Clear the view
	 */
	void clear();
}
