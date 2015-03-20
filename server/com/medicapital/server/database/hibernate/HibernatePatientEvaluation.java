package com.medicapital.server.database.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.database.DaoPatientEvaluation;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.PatientEvaluationEntity;
import com.medicapital.server.database.hibernate.entities.PatientVisitEntity;
import com.medicapital.server.database.hibernate.transform.PatientEvaluationTransformer;
import com.medicapital.server.database.hibernate.transform.Transformer;

public class HibernatePatientEvaluation extends HibernateEntityAccess<PatientEvaluation, PatientEvaluationEntity> implements DaoPatientEvaluation {

	private final PatientEvaluationTransformer transformer = new PatientEvaluationTransformer();

	@Override
	final public int create(final PatientEvaluation evaluation) throws DataAccessException {
		try {
			if (evaluation.getVisitId() <= 0) {
				throw new DataAccessException("Couldn't create evaluation becasue visitId not set");
			}
			final int newEvaluationId = super.create(evaluation);
			// TODO triger should do the trick
			// add evaluation into visit
			final Query query = getSession().createQuery("update " + PatientVisitEntity.class.getSimpleName() + " v set v.evaluationEntity.evaluationId=? where visitId=?");
			query.setInteger(0, newEvaluationId);
			query.setInteger(1, evaluation.getVisitId());
			if (query.executeUpdate() <= 0) {
				try {
					delete(newEvaluationId);
				} catch (DataAccessException e) {
					// ignore
				}
				throw new DataAccessException("Couldn't assigned evaluation: " + newEvaluationId + " into visit: " + evaluation.getVisitId());
			}
			return newEvaluationId;
		} catch (DataAccessException e) {
			throw e;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getDoctorEvaluationsCount(final int doctorId) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("select count(*) from " + PatientEvaluationEntity.class.getSimpleName() + " ev where evaluationId in (select v.evaluationEntity.evaluationId from " + PatientVisitEntity.class.getSimpleName() + " v where doctorId=:doctorId)");
			query.setInteger("doctorId", doctorId);
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<PatientEvaluation> getDoctorPatientEvaluations(final int doctorId, final int startRow, final int rowNumber) throws DataAccessException {
		try {
			final Query query = getSession().createQuery("from " + PatientEvaluationEntity.class.getSimpleName() + " ev where evaluationId in (select v.evaluationEntity.evaluationId from " + PatientVisitEntity.class.getSimpleName() + " v where doctorId=:doctorId)");
			query.setInteger("doctorId", doctorId);
			query.setFirstResult(startRow);
			query.setMaxResults(rowNumber);
			@SuppressWarnings("unchecked")
			final List<PatientEvaluationEntity> rows = query.list();
			return HibernateDaoUtil.toPojoSet(rows, transformer);
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	protected Class<PatientEvaluationEntity> getPersistenceClass() {
		return PatientEvaluationEntity.class;
	}

	@Override
	protected Class<PatientEvaluation> getPojoClass() {
		return PatientEvaluation.class;
	}

	@Override
	protected Transformer<PatientEvaluation, PatientEvaluationEntity> getTransformer() {
		return new PatientEvaluationTransformer();
	}

}
