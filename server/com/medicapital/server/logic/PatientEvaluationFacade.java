package com.medicapital.server.logic;

import java.util.Set;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DaoPatientEvaluation;
import com.medicapital.server.security.Secured;

public class PatientEvaluationFacade extends EntityFacade<PatientEvaluation> {

	private DaoPatientEvaluation daoPatientEvaluation;

	@Secured
	public int getDoctorEvaluationsCount(int doctorId) {
		return daoPatientEvaluation.getDoctorEvaluationsCount(doctorId);
	}

	@Secured
	public Set<PatientEvaluation> getDoctorPatientEvaluations(int doctorId, int startRow, int rowCount) {
		return daoPatientEvaluation.getDoctorPatientEvaluations(doctorId, startRow, rowCount);
	}

	@Secured
	@Override
	public int create(PatientEvaluation entity) throws ServerException {
		return super.create(entity);
	}

	@Secured
	@Override
	public void delete(int entityId) throws ServerException {
		throw new ServerException("Operation not allowed");
	}

	@Secured
	@Override
	public void update(PatientEvaluation entity) throws ServerException {
		throw new ServerException("Operation not allowed");
	}

	@Override
	protected DaoEntityAccess<PatientEvaluation> getDao() {
		return daoPatientEvaluation;
	}

	public void setDaoPatientEvaluation(DaoPatientEvaluation daoPatientEvaluation) {
		this.daoPatientEvaluation = daoPatientEvaluation;
	}

}
