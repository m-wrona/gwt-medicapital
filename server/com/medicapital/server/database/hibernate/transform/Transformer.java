package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.SerializableEntity;

/**
 * Transformer between POJO and Persistence entities.
 * 
 * @author mwronski
 * 
 * @param <E>
 *            POJO entity
 * @param <P>
 *            Persistence entity
 */
public interface Transformer<E extends SerializableEntity, P> {

	E createPojo(P persistenceEntity);

	P createEntity(E pojoEntity);
	
	P createEntity(int entityId);
}
