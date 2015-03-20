package com.medicapital.server.database.hibernate;

import java.util.List;
import org.hibernate.Query;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.transform.Transformer;

/**
 * Generic hibernate access to entity
 * 
 * @author mwronski
 * 
 * @param <T>
 *            POJO entity type
 * @param <P>
 *            persistence entity type
 */
abstract class HibernateEntityAccess<T extends SerializableEntity, P> extends HibernateAccess implements DaoEntityAccess<T> {

	@Override
	public int getCount() throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + getPersistenceClass().getSimpleName());
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int create(T entity) throws DataAccessException {
		try {
			final P persistenceEntity = getTransformer().createEntity(entity);
			return (Integer) getHibernateTemplate().save(persistenceEntity);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void update(T entity) throws DataAccessException {
		try {
			final P persistenceEntity = getTransformer().createEntity(entity);
			getHibernateTemplate().update(persistenceEntity);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}

	}

	@Override
	public void delete(int entityId) throws DataAccessException {
		try {
			final P persistenceEntity = getTransformer().createEntity(entityId);
			getHibernateTemplate().delete(persistenceEntity);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}

	}

	@Override
	public T get(int entityId) throws DataAccessException {
		try {
			final P persistenceEntity = getHibernateTemplate().get(getPersistenceClass(), entityId);
			T entity = getTransformer().createPojo(persistenceEntity);
			return entity;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	/**
	 * Get persistence entity class
	 * 
	 * @return
	 */
	abstract protected Class<P> getPersistenceClass();

	/**
	 * Get POJO entity class
	 * 
	 * @return
	 */
	abstract protected Class<T> getPojoClass();

	/**
	 * Get entity transformer
	 * 
	 * @return
	 */
	abstract protected Transformer<T, P> getTransformer();

}
