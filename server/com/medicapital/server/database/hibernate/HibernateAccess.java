package com.medicapital.server.database.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.medicapital.server.database.DaoAccess;
import com.medicapital.server.database.DataAccessException;

class HibernateAccess extends HibernateDaoSupport implements DaoAccess {

	@Override
	public void clean() throws DataAccessException {
		try {
			getHibernateTemplate().clear();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void close() throws DataAccessException {
		clean();
		try {
			getHibernateTemplate().getSessionFactory().close();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

}
