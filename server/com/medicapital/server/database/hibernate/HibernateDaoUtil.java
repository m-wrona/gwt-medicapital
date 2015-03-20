package com.medicapital.server.database.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.server.database.hibernate.transform.Transformer;

final public class HibernateDaoUtil {

	public static <E extends SerializableEntity, P> Set<P> toEntitySet(final Collection<E> pojos, final Transformer<E, P> transformer) {
		if (pojos == null || pojos.isEmpty()) {
			return null;
		}
		final Set<P> entities = new LinkedHashSet<P>();
		for (final E pojo : pojos) {
			entities.add(transformer.createEntity(pojo));
		}
		return entities;
	}

	public static <E extends SerializableEntity, P> Set<E> toPojoSet(final Collection<P> persistenceEntities, final Transformer<E, P> transformer) {
		if (persistenceEntities == null || persistenceEntities.isEmpty()) {
			return null;
		}
		final Set<E> pojos = new LinkedHashSet<E>();
		for (final P entity : persistenceEntities) {
			pojos.add(transformer.createPojo(entity));
		}
		return pojos;
	}

	public static <E extends SerializableEntity, P> List<E> toPojoList(final Collection<P> persistenceEntities, final Transformer<E, P> transformer) {
		if (persistenceEntities == null || persistenceEntities.isEmpty()) {
			return null;
		}
		final List<E> pojos = new ArrayList<E>();
		for (final P entity : persistenceEntities) {
			pojos.add(transformer.createPojo(entity));
		}
		return pojos;
	}

	private HibernateDaoUtil() {
		// empty
	}
}
