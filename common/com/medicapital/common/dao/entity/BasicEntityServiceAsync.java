package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Basic entity GWT-RPC access
 * 
 * @author mwronski
 * 
 * @param <E>
 *            entity type
 */
public interface BasicEntityServiceAsync<E extends SerializableEntity> {

	void get(int entityId, AsyncCallback<E> callback);

	void create(E entity, AsyncCallback<Integer> callback);

	void update(E entity, AsyncCallback<Boolean> callback);

	void delete(int entityId, AsyncCallback<Boolean> callback);
}
