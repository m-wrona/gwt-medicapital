package com.medicapital.client.core.entities;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface allows to display list of elements which can be modified by user
 * 
 * @author mwronski
 * 
 * @param <E>
 */
public interface EditableEntitiesView<E extends SerializableEntity> extends EntitiesView<E> {

	/**
	 * Set delete selected elements handler enabled
	 * 
	 * @param enabled
	 */
	void setDeleteSelectedHandlerEnabled(boolean enabled);

	/**
	 * Select all elements in the list
	 * 
	 * @param select
	 */
	void setSelectAllEntities(boolean select);

}
