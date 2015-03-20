package com.medicapital.server.database.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import com.medicapital.common.entities.Hobby;
import com.medicapital.server.database.DaoHobby;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.transform.HobbyTransformer;

public class HibernateHobby extends HibernateAccess implements DaoHobby {

	private final HobbyTransformer transformer = new HobbyTransformer();

	@Override
	public int getHobbiesCount() throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + HobbyEntity.class.getSimpleName());
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<Hobby> getHobbies() throws DataAccessException {
		try {
			@SuppressWarnings("unchecked")
			List<HobbyEntity> results = getHibernateTemplate().find("from " + HobbyEntity.class.getSimpleName());
			return HibernateDaoUtil.toPojoSet(results, transformer);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

}
