package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.RemoteService;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Basic entity access
 * 
 * @author mwronski
 * 
 * @param <E>
 *            entity type
 */
public interface BasicEntityService<E extends SerializableEntity> extends RemoteService {

	E get(int entityId);

	int create(E entity);

	boolean update(E entity);

	boolean delete(int entityId);
}
