package com.medicapital.server.database.hibernate;

import java.util.List;
import org.hibernate.Query;
import com.medicapital.common.entities.VisitType;
import com.medicapital.server.database.DaoVisitType;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.VisitTypeEntity;
import com.medicapital.server.database.hibernate.transform.Transformer;
import com.medicapital.server.database.hibernate.transform.VisitTypeTransformer;

public class HibernateVisitType extends HibernateEntityAccess<VisitType, VisitTypeEntity> implements DaoVisitType {

	private final VisitTypeTransformer transformer = new VisitTypeTransformer();

	@Override
	public List<VisitType> getDoctorVisitTypes(int doctorId) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + VisitTypeEntity.class.getSimpleName() + " where doctorId=:doctorId");
			query.setInteger("doctorId", doctorId);
			@SuppressWarnings("unchecked")
			final List<VisitTypeEntity> visitTypes = (List<VisitTypeEntity>) query.list();
			return HibernateDaoUtil.toPojoList(visitTypes, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	protected Class<VisitTypeEntity> getPersistenceClass() {
		return VisitTypeEntity.class;
	}

	@Override
	protected Class<VisitType> getPojoClass() {
		return VisitType.class;
	}

	@Override
	protected Transformer<VisitType, VisitTypeEntity> getTransformer() {
		return new VisitTypeTransformer();
	}

}
