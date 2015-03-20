package com.medicapital.server.database.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import com.medicapital.common.entities.Specialization;
import com.medicapital.server.database.DaoSpecialization;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;
import com.medicapital.server.database.hibernate.transform.SpecializationTransformer;

public class HibernateSpecialization extends HibernateAccess implements DaoSpecialization {

	private final SpecializationTransformer transformer = new SpecializationTransformer();

	@Override
	public int getSpiecializationCount() throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + SpecializationEntity.class.getSimpleName());
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<Specialization> getSpecializations() throws DataAccessException {
		try {
			@SuppressWarnings("unchecked")
			final List<SpecializationEntity> results = getHibernateTemplate().find("from " + SpecializationEntity.class.getSimpleName());
			return HibernateDaoUtil.toPojoSet(results, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

}
