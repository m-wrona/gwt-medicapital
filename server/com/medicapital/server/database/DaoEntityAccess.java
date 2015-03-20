package com.medicapital.server.database;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Generic access to entity
 * 
 * @author mwronski
 * 
 * @param <T>
 *            entity type
 */
public interface DaoEntityAccess<T extends SerializableEntity> extends DaoAccess {

	/**
	 * Create entity
	 * 
	 * @param entity
	 * @return ID of newly created entity
	 * @throws DataAccessException
	 */
	int create(T entity) throws DataAccessException;

	/**
	 * Update entity
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	void update(T entity) throws DataAccessException;

	/**
	 * Delete entity
	 * 
	 * @param entityId
	 * @throws DataAccessException
	 */
	void delete(int entityId) throws DataAccessException;

	/**
	 * Get entity
	 * 
	 * @param entityId
	 * @return
	 * @throws DataAccessException
	 */
	T get(int entityId) throws DataAccessException;

	/**
	 * Get total number of entities
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getCount() throws DataAccessException;

}
