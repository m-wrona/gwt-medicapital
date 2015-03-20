package com.medicapital.common.validation;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Validator checks if entity is in proper state or not.
 * 
 * @author mwronski
 * 
 * @param <E>
 */
public interface EntityValidator<E extends SerializableEntity> {

	/**
	 * Check if entity is valid
	 * 
	 * @param entity
	 * @return
	 */
	boolean validate(E entity);
}
