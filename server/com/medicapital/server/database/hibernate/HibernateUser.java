package com.medicapital.server.database.hibernate;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.medicapital.common.entities.User;
import com.medicapital.server.database.DaoUser;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.criteria.UserEntityCriteria;
import com.medicapital.server.database.hibernate.entities.UserEntity;
import com.medicapital.server.database.hibernate.transform.Transformer;
import com.medicapital.server.database.hibernate.transform.UserTransformer;

@Repository
public class HibernateUser extends HibernateEntityAccess<User, UserEntity> implements DaoUser {

	private final UserTransformer transformer = new UserTransformer();

	@SuppressWarnings("unchecked")
	@Override
	public List<User> searchUsers(User searchCriteria, int startRow, int maxRows) throws DataAccessException {
		try {
			final UserEntity userSearchCriteria = transformer.createEntity(searchCriteria);
			final DetachedCriteria userCriteria = new UserEntityCriteria().createSelect(userSearchCriteria);
			final List<UserEntity> userEntities = getHibernateTemplate().findByCriteria(userCriteria, startRow, maxRows);
			return HibernateDaoUtil.toPojoList(userEntities, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int searchUsersCount(User searchCriteria) throws DataAccessException {
		try {
			final UserEntity userSearchCriteria = transformer.createEntity(searchCriteria);
			final DetachedCriteria userCriteria = new UserEntityCriteria().createSelectCount(userSearchCriteria);
			final List<Long> result = getHibernateTemplate().findByCriteria(userCriteria);
			return result.isEmpty() ? 0 : result.get(0).intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public User get(final String login) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + UserEntity.class.getSimpleName() + " where login = :login");
			query.setString("login", login);
			final UserEntity userEntity = (UserEntity) query.uniqueResult();
			return transformer.createPojo(userEntity);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void delete(final String login) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("delete " + UserEntity.class.getSimpleName() + " where login= :login");
			query.setString("login", login);
			query.executeUpdate();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	protected Class<UserEntity> getPersistenceClass() {
		return UserEntity.class;
	}

	@Override
	protected Class<User> getPojoClass() {
		return User.class;
	}

	@Override
	protected Transformer<User, UserEntity> getTransformer() {
		return new UserTransformer();
	}

}
