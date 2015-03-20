package com.medicapital.server.database.hibernate;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import com.medicapital.client.exception.UnsupportedOperationException;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.server.database.DaoDoctor;
import com.medicapital.server.database.DaoUser;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.criteria.DoctorEntityCriteria;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;
import com.medicapital.server.database.hibernate.transform.DoctorTransformer;
import com.medicapital.server.database.hibernate.transform.Transformer;

public class HibernateDoctor extends HibernateEntityAccess<Doctor, DoctorEntity> implements DaoDoctor {

	private final DoctorTransformer transformer = new DoctorTransformer();
	private DaoUser daoUser;

	@Override
	public int create(Doctor entity) throws DataAccessException {
		entity.getUser().setUserRole(UserRole.Doctor);
		return super.create(entity);
	}

	@Override
	public void update(Doctor entity) throws DataAccessException {
		entity.getUser().setUserRole(UserRole.Doctor);
		super.update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int searchDoctorCount(final SearchDoctorCriteria searchCriteria) throws DataAccessException {
		try {
			final DetachedCriteria selectCountCriteria = new DoctorEntityCriteria().createSelectCount(searchCriteria);
			final List<Long> result = getHibernateTemplate().findByCriteria(selectCountCriteria);
			return result.isEmpty() ? 0 : result.get(0).intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctor> searchDoctors(final SearchDoctorCriteria searchCriteria) throws DataAccessException {
		try {
			final DetachedCriteria doctorCriteria = new DoctorEntityCriteria().createSelect(searchCriteria);
			final List<DoctorEntity> doctorEntities = getHibernateTemplate().findByCriteria(doctorCriteria, searchCriteria.getStartRow(), searchCriteria.getRowCount());
			return HibernateDaoUtil.toPojoList(doctorEntities, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Doctor get(final String login) throws DataAccessException {
		try {
			// prepare search criteria
			final DoctorEntity doctorSearchEntity = new DoctorEntity();
			final UserEntity userEntity = new UserEntity();
			userEntity.setUserRole(UserRole.Doctor.toString());
			userEntity.setLogin(login);
			doctorSearchEntity.setUserEntity(userEntity);
			final DetachedCriteria doctorCriteria = new DoctorEntityCriteria().createSelect(doctorSearchEntity);
			// search doctor
			final List<DoctorEntity> doctorEntities = getHibernateTemplate().findByCriteria(doctorCriteria);
			return doctorEntities.isEmpty() ? null : transformer.createPojo(doctorEntities.get(0));
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte[] getProfilePhoto(final int doctorId) throws DataAccessException {
		try {
			final Query query = getSession().createSQLQuery("select photo from doctor where doctorId = :doctorId");
			query.setInteger("doctorId", doctorId);
			return (byte[]) query.uniqueResult();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte[] getGalleryPhoto(int doctorId, int photoNumber) throws DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void setDaoUser(DaoUser daoUser) {
		this.daoUser = daoUser;
	}

	public DaoUser getDaoUser() {
		return daoUser;
	}

	@Override
	protected Class<DoctorEntity> getPersistenceClass() {
		return DoctorEntity.class;
	}

	@Override
	protected Class<Doctor> getPojoClass() {
		return Doctor.class;
	}

	@Override
	protected Transformer<Doctor, DoctorEntity> getTransformer() {
		return new DoctorTransformer();
	}

}
