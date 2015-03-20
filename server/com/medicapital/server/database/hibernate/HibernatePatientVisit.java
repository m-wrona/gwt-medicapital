package com.medicapital.server.database.hibernate;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.server.database.DaoDoctor;
import com.medicapital.server.database.DaoPatientVisit;
import com.medicapital.server.database.DaoUser;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.PatientVisitEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;
import com.medicapital.server.database.hibernate.transform.PatientVisitTransformer;
import com.medicapital.server.database.hibernate.transform.Transformer;

public class HibernatePatientVisit extends HibernateEntityAccess<PatientVisit, PatientVisitEntity> implements DaoPatientVisit {

	private final PatientVisitTransformer transformer = new PatientVisitTransformer();
	private DaoUser daoUser;
	private DaoDoctor daoDoctor;

	@Override
	public void updateLockedVisit(final int visitId, final Date startTime, final Date endTime, final Date bookingTime, String visitTitle) throws DataAccessException {
		PatientVisit visit = get(visitId);
		if (visit == null) {
			throw new DataAccessException("Booked visit does not exist - visitId: " + visitId);
		}
		visit.setTitle(visitTitle);
		visit.setStartTime(startTime);
		visit.setEndTime(endTime);
		visit.setLockTime(bookingTime);
		update(visit);
	}

	@Override
	public int lockVisit(final int patientId, final int doctorId, final Date startTime, final Date endTime, final Date bookingTime, String visitTitle) throws DataAccessException {
		Doctor doctor = daoDoctor.get(doctorId);
		if (doctor == null) {
			throw new DataAccessException("Unknown doctor: " + doctorId);
		}
		PatientVisit visit = new PatientVisit();
		visit.setTitle(visitTitle);
		visit.setStartTime(startTime);
		visit.setEndTime(endTime);
		visit.setLockTime(bookingTime);
		User patient = new User();
		patient.setId(patientId);
		visit.setPatient(patient);
		visit.setDoctor(doctor);
		visit.setLocalization(doctor.getUser().getLocalization());
		return create(visit);
	}

	@Override
	public boolean isVisitTermExpendable(final int doctorId, final Date newStartTime, final Date newEndTime) throws DataAccessException {
		final List<?> results = createCheckVisitTermQuery(doctorId, newStartTime, newEndTime).list();
		// if more then one result is returned that means that other meetings
		// are already planned
		return results == null ? true : (results.size() == 1 ? true : false);
	}

	@Override
	public boolean isVisitTermFree(final int doctorId, final Date startTime, final Date endTime) throws DataAccessException {
		final List<?> results = createCheckVisitTermQuery(doctorId, startTime, endTime).list();
		return results == null ? true : (results.isEmpty() ? true : false);
	}

	/**
	 * Create query which enables to check whether some visits are planned in
	 * giving scope
	 * 
	 * @param doctorId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private SQLQuery createCheckVisitTermQuery(final int doctorId, final Date startTime, final Date endTime) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select visitId from patientVisit where doctorId=:doctorId and ").append(conditionVisitTimeScope("beginTime", "endTime"));
		sql.append(" union ");
		sql.append("select eventId from calendarEvent where userId = (select userId from doctor where doctorId=:doctorId) and ").append(conditionVisitTimeScope("startTime", "endTime"));
		final SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setInteger("doctorId", doctorId);
		query.setTimestamp("startTime", startTime);
		query.setTimestamp("endTime", endTime);
		return query;
	}

	/**
	 * Create SQL condition for checking scope of term
	 * 
	 * @param beginColumn
	 *            name of begin term's time column
	 * @param endColumn
	 *            name of end term's time column
	 * @return
	 */
	private String conditionVisitTimeScope(String beginColumn, String endColumn) {
		StringBuilder timeScope = new StringBuilder("(");
		// start time inside term scope
		timeScope.append("(:startTime>=" + beginColumn + " and :startTime<" + endColumn + ")");
		timeScope.append(" or ");
		// end time inside term scope
		timeScope.append("(:endTime>" + beginColumn + " and :endTime<=" + endColumn + ")");
		timeScope.append(" or ");
		// time scope wider then term
		timeScope.append("(" + beginColumn + ">=:startTime and :endTime>=" + endColumn + ")");
		timeScope.append(")");
		return timeScope.toString();
	}

	@Override
	public Set<PatientVisit> getDoctorVisits(final int doctorId, final Date beginDate, final Date endDate) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + PatientVisitEntity.class.getSimpleName() + " where doctorId = :doctorId and beginTime>:beginDate and beginTime<:endDate order by beginTime desc");
			query.setInteger("doctorId", doctorId);
			query.setTimestamp("beginDate", new Time(beginDate.getTime()));
			query.setTimestamp("endDate", new Time(endDate.getTime()));
			@SuppressWarnings("unchecked")
			final List<PatientVisitEntity> visits = query.list();
			return HibernateDaoUtil.toPojoSet(visits, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<PatientVisit> getDoctorVisits(int doctorId, int startRow, int rowNumbers) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + PatientVisitEntity.class.getSimpleName() + " where doctorId = :doctorId order by beginTime desc");
			query.setInteger("doctorId", doctorId);
			query.setFirstResult(startRow);
			query.setMaxResults(rowNumbers);
			@SuppressWarnings("unchecked")
			final List<PatientVisitEntity> visits = query.list();
			return HibernateDaoUtil.toPojoSet(visits, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getDoctorPatientVisitsCount(final int doctorId) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + PatientVisitEntity.class.getSimpleName() + " where doctorId=:doctorId");
			query.setInteger("doctorId", doctorId);
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getPatientVisitsCount(final String patientLogin) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + PatientVisitEntity.class.getSimpleName() + " v where patient.userId = (select userId from " + UserEntity.class.getSimpleName() + " u where login=:login)");
			query.setString("login", patientLogin);
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<PatientVisit> getPatientVisits(final String patientLogin, final int startRow, final int rowNumbers) throws DataAccessException {
		try {
			if (daoUser == null) {
				throw new IllegalArgumentException("DaoUser not set");
			}
			final User user = daoUser.get(patientLogin);
			if (user == null) {
				return new HashSet<PatientVisit>();
			}
			final Query query = getSession().createQuery("from " + PatientVisitEntity.class.getSimpleName() + " where patientId = :patientId order by beginTime desc");
			query.setInteger("patientId", user.getId());
			query.setFirstResult(startRow);
			query.setMaxResults(rowNumbers);
			@SuppressWarnings("unchecked")
			final List<PatientVisitEntity> visits = query.list();
			return HibernateDaoUtil.toPojoSet(visits, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	public void setDaoUser(final DaoUser daoUser) {
		this.daoUser = daoUser;
	}

	public DaoUser getDaoUser() {
		return daoUser;
	}

	public void setDaoDoctor(DaoDoctor daoDoctor) {
		this.daoDoctor = daoDoctor;
	}

	@Override
	protected Class<PatientVisitEntity> getPersistenceClass() {
		return PatientVisitEntity.class;
	}

	@Override
	protected Class<PatientVisit> getPojoClass() {
		return PatientVisit.class;
	}

	@Override
	protected Transformer<PatientVisit, PatientVisitEntity> getTransformer() {
		return new PatientVisitTransformer();
	}

}
