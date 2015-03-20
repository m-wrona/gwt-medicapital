package com.medicapital.server.database.hibernate;

import java.util.Date;
import org.hibernate.Query;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.entities.UserRole;
import com.medicapital.server.database.DaoLogin;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class HibernateLogin extends HibernateAccess implements DaoLogin {

	@Override
	public LoginData loggin(final String login, final char[] password) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + UserEntity.class.getSimpleName() + " where login = :login and password = :password");
			query.setString("login", login);
			query.setString("password", new String(password));
			final UserEntity userEntity = (UserEntity) query.uniqueResult();
			if (userEntity == null) {
				return null;
			}
			final LoginData loginData = new LoginData();
			loginData.setId(userEntity.getUserId());
			loginData.setLogin(userEntity.getLogin());
			loginData.setLastLoginDate(userEntity.getLastLoginDate());
			loginData.setRole(UserRole.valueOf(userEntity.getUserRole()));
			return loginData;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public boolean changePassword(String login, final String eMail, final char[] password) throws DataAccessException {
		try {
			final Query updateQuery = getSession().createQuery("update " + UserEntity.class.getSimpleName() + " set password=:password where login=:login and eMail = :eMail");
			updateQuery.setString("login", login);
			updateQuery.setString("eMail", eMail);
			updateQuery.setString("password", new String(password));
			return updateQuery.executeUpdate() == 1;
		} catch (DataAccessException e) {
			throw e;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void setLastLoginDate(final String login, final Date date) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("update " + UserEntity.class.getSimpleName() + " set lastLoginDate = :lastLoginDate where login = :login");
			query.setString("login", login);
			query.setTimestamp("lastLoginDate", date);
			query.executeUpdate();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}

	}

}
