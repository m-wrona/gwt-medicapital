package com.medicapital.server.access.servlet;

import static com.medicapital.server.log.Tracer.tracer;
import com.medicapital.common.dao.entity.BasicEntityService;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.server.logic.EntityFacade;

/**
 * Servlet implements basic entity access
 * 
 * @author mwronski
 * 
 * @param <E>
 */
abstract class BasicServiceServlet<E extends SerializableEntity> implements BasicEntityService<E> {

	@Override
	public final E get(int entityId) {
		tracer(this).debug("Get entity - entityId: " + entityId);
		return getEntityFacade().get(entityId);
	}

	@Override
	public final int create(E entity) {
		tracer(this).debug("Create entity: " + entity);
		return getEntityFacade().create(entity);
	}

	@Override
	public final boolean update(E entity) {
		tracer(this).debug("Update entity: " + entity);
		getEntityFacade().update(entity);
		return true;
	}

	@Override
	public final boolean delete(int entityId) {
		tracer(this).debug("Delete entity - entityId: " + entityId);
		getEntityFacade().delete(entityId);
		return true;
	}

	abstract protected EntityFacade<E> getEntityFacade();
}
