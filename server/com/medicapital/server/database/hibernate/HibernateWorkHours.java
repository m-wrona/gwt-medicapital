package com.medicapital.server.database.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import com.medicapital.common.entities.WorkHours;
import com.medicapital.server.database.DaoWorkHours;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.WorkHoursEntity;
import com.medicapital.server.database.hibernate.transform.Transformer;
import com.medicapital.server.database.hibernate.transform.WorkHoursTransformer;

public class HibernateWorkHours extends HibernateEntityAccess<WorkHours, WorkHoursEntity> implements DaoWorkHours {

	@Override
	public List<WorkHours> getDoctorWorkHours(int doctorId, Date dateFrom, Date dateTo, int startRow, int rowCount) throws DataAccessException {
		final Query query = getSession().createQuery("from " + WorkHoursEntity.class.getSimpleName() + " where doctorId = :doctorId and (day!=null or (dateFrom>=:dateFrom and dateTo<=:dateTo)) order by day desc");
		query.setInteger("doctorId", doctorId);
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		query.setFirstResult(startRow);
		query.setMaxResults(rowCount);
		@SuppressWarnings("unchecked")
		List<WorkHoursEntity> workHours = query.list();
		return HibernateDaoUtil.toPojoList(workHours, getTransformer());
	}

	@Override
	public int getDoctorWorkHoursCount(int doctorId, Date dateFrom, Date dateTo) throws DataAccessException {
		final Query query = getSession().createQuery("select count(*) from " + WorkHoursEntity.class.getSimpleName() + " where doctorId = :doctorId and (day!=null or (dateFrom>=:dateFrom and dateTo<=:dateTo))");
		query.setInteger("doctorId", doctorId);
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		// get results
		final List<?> result = query.list();
		final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
		return count.intValue();
	}

	@Override
	protected Class<WorkHoursEntity> getPersistenceClass() {
		return WorkHoursEntity.class;
	}

	@Override
	protected Class<WorkHours> getPojoClass() {
		return WorkHours.class;
	}

	@Override
	protected Transformer<WorkHours, WorkHoursEntity> getTransformer() {
		return new WorkHoursTransformer();
	}

}
