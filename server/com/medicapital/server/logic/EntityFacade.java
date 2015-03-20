package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.server.database.DaoEntityAccess;

/**
 * Business logic related with entity
 * 
 * @author mwronski
 * 
 * @param <T>
 *            entity type
 */
public abstract class EntityFacade<T extends SerializableEntity> {

	/**
	 * Get total number of entities
	 * 
	 * @return
	 * @throws ServerException
	 */
	public int getCount() throws ServerException {
		tracer(this).debug("Getting total number of rows");
		return getDao().getCount();
	}

	/**
	 * Create entity
	 * 
	 * @param entity
	 * @return ID of newly created entity
	 * @throws ServerException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public int create(T entity) throws ServerException {
		tracer(this).debug("Creating entity: " + entity);
		validateEntity(entity);
		return getDao().create(entity);
	}

	/**
	 * Delete entity
	 * 
	 * @param entityId
	 * @throws ServerException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void delete(int entityId) throws ServerException {
		tracer(this).debug("Deleting entity: " + entityId);
		getDao().delete(entityId);
	}

	/**
	 * Get entity
	 * 
	 * @param entityId
	 * @return
	 * @throws ServerException
	 */
	public T get(int entityId) throws ServerException {
		tracer(this).debug("Get entity: " + entityId);
		return getDao().get(entityId);
	}

	/**
	 * Update entity
	 * 
	 * @param entity
	 * @throws ServerException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void update(T entity) throws ServerException {
		tracer(this).debug("Updating entity: " + entity);
		validateEntity(entity);
		getDao().update(entity);
	}

	/**
	 * Validate entity
	 * 
	 * @param entity
	 * @throws ServerException
	 */
	protected void validateEntity(T entity) throws ServerException {
		// empty
	}

	/**
	 * Get DAO access
	 * 
	 * @return
	 */
	abstract protected DaoEntityAccess<T> getDao();
}
