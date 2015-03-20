package com.medicapital.server.database;

import java.util.Date;
import java.util.List;
import com.medicapital.common.entities.WorkHours;

public interface DaoWorkHours extends DaoEntityAccess<WorkHours> {

	/**
	 * Get doctor's work hours from all days of week and from given time tables
	 * (special events like holidays etc.)
	 * 
	 * @param doctorId
	 * @param dateFrom
	 * @param dateTo
	 * @param startRow
	 * @param rowCount
	 * @return
	 * @throws DataAccessException
	 */
	List<WorkHours> getDoctorWorkHours(int doctorId, Date dateFrom, Date dateTo, int startRow, int rowCount) throws DataAccessException;

	/**
	 * Get doctor's work hours count from all days of week and from given time
	 * tables (special events like holidays etc.)
	 * 
	 * @param doctorId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws DataAccessException
	 */
	int getDoctorWorkHoursCount(int doctorId, Date dateFrom, Date dateTo) throws DataAccessException;
}
