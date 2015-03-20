package com.medicapital.server.database;

import java.util.Date;
import java.util.Set;
import com.medicapital.common.entities.PatientVisit;

/**
 * Interface enables to perform operations on {@link PatientVisit}.
 * 
 * @author mwronski
 * 
 */
public interface DaoPatientVisit extends DaoEntityAccess<PatientVisit> {

	/**
	 * Update locked visit
	 * 
	 * @param visitId
	 * @param startTime
	 * @param endTime
	 * @param lockTime
	 *            time which should be set to say when reservation can be
	 *            canceled
	 * @param visitTitle
	 * @return visit instance with data of doctor and patient
	 * @throws DataAccessException
	 */
	void updateLockedVisit(int visitId, Date startTime, Date endTime, Date lockTime, String visitTitle) throws DataAccessException;

	/**
	 * Create lock for a visit
	 * 
	 * @param patientId
	 * @param doctorId
	 * @param startTime
	 * @param endTime
	 * @param lockTime
	 *            time which should be set to say when reservation can be
	 *            canceled
	 * @param visitTitle
	 * @return visit instance with data of doctor and patient
	 * @throws DataAccessException
	 */
	int lockVisit(int patientId, int doctorId, Date startTime, Date endTime, Date lockTime, String visitTitle) throws DataAccessException;

	/**
	 * Check whether visit can be expanded using new time boundaries
	 * 
	 * @param doctorId
	 * @param newStartTime
	 * @param newEndTime
	 * @return true if given time boundaries are available, false otherwise
	 * @throws DataAccessException
	 */
	boolean isVisitTermExpendable(int doctorId, Date newStartTime, Date newEndTime) throws DataAccessException;

	/**
	 * Check whether visit term is available
	 * 
	 * @param doctorId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws DataAccessException
	 */
	boolean isVisitTermFree(int doctorId, Date startTime, Date endTime) throws DataAccessException;

	/**
	 * Get visits count for a patient
	 * 
	 * @param patientLogin
	 * @return
	 * @throws DataAccessException
	 */
	int getPatientVisitsCount(String patientLogin) throws DataAccessException;

	/**
	 * Get visits count for a doctor
	 * 
	 * @param doctorId
	 * @return
	 * @throws DataAccessException
	 */
	int getDoctorPatientVisitsCount(int doctorId) throws DataAccessException;

	/**
	 * Get visits of a patient
	 * 
	 * @param patientLogin
	 * @param startRow
	 * @param rowNumbers
	 * @return
	 * @throws DataAccessException
	 */
	Set<PatientVisit> getPatientVisits(String patientLogin, int startRow, int rowNumbers) throws DataAccessException;

	/**
	 * Get doctor's visits between given dates
	 * 
	 * @param doctorId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws DataAccessException
	 */
	Set<PatientVisit> getDoctorVisits(int doctorId, Date beginDate, Date endDate) throws DataAccessException;

	/**
	 * Get visits
	 * 
	 * @param doctorId
	 * @param startRow
	 * @param rowNumbers
	 * @return
	 * @throws DataAccessException
	 */
	Set<PatientVisit> getDoctorVisits(int doctorId, int startRow, int rowNumbers) throws DataAccessException;

}
