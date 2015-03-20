package com.medicapital.client.core.entity;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface enables editing element.
 * 
 * @author michal
 * 
 * @param <E>
 */
public interface EditEntityView<E extends SerializableEntity> extends EntityView {

	/**
	 * Make delete handler enabled
	 * 
	 * @param enabled
	 */
	void setDeleteHandlerEnabled(boolean enabled);

	/**
	 * Make save handler enabled
	 * 
	 * @param enabled
	 */
	void setSaveHandlerEnabled(boolean enabled);
	
}
