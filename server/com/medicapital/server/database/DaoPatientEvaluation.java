package com.medicapital.server.database;

import java.util.Set;
import com.medicapital.common.entities.PatientEvaluation;

public interface DaoPatientEvaluation extends DaoEntityAccess<PatientEvaluation> {

	/**
	 * Get number of evaluation of doctor
	 * 
	 * @param doctorId
	 * @return
	 * @throws DataAccessException
	 */
	int getDoctorEvaluationsCount(int doctorId) throws DataAccessException;

	/**
	 * Get evaluations of doctor
	 * 
	 * @param doctorId
	 * @param startRow
	 * @param rowNumber
	 * @return
	 * @throws DataAccessException
	 */
	Set<PatientEvaluation> getDoctorPatientEvaluations(int doctorId, int startRow, int rowNumber) throws DataAccessException;

}
