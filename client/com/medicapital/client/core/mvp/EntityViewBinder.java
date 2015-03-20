package com.medicapital.client.core.mvp;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Binder allows to display entity on view in proper manner
 * 
 * @author mwronski
 * 
 * @param <E>
 */
public interface EntityViewBinder<E extends SerializableEntity> {

	/**
	 * Get entity with data from view
	 * 
	 * @param sourceEntity
	 *            which may be needed to copy IDs, not displayed data etc.
	 * @return
	 */
	E getEntityFromView(E sourceEntity);

	/**
	 * Display entity on view
	 * 
	 * @param entity
	 */
	void display(E entity);

	/**
	 * Clear view
	 */
	void clear();
}
