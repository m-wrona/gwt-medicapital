package com.medicapital.server.database.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import com.medicapital.common.entities.City;
import com.medicapital.server.database.DaoCity;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.transform.CityTransformer;

public class HibernateCity extends HibernateAccess implements DaoCity {

	private final CityTransformer transformer = new CityTransformer();

	@Override
	public int getCitiesCount() throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + CityEntity.class.getSimpleName());
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<City> getCities() throws DataAccessException {
		try {
			@SuppressWarnings("unchecked")
			List<CityEntity> results = getHibernateTemplate().find("from " + CityEntity.class.getSimpleName());
			return HibernateDaoUtil.toPojoSet(results, transformer);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

}
