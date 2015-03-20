package com.medicapital.client.core.entity;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface enables to create element
 * 
 * @author michal
 * 
 * @param <E>
 */
public interface CreateEntityView<E extends SerializableEntity> extends EntityView {

	/**
	 * Make save handler enabled
	 * 
	 * @param enabled
	 */
	void setSaveHandlerEnabled(boolean enabled);
	
}
