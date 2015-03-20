package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Date;
import java.util.List;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.WorkHours;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DaoWorkHours;
import com.medicapital.server.security.Secured;

public class WorkHoursFacade extends EntityFacade<WorkHours> {

	private DaoWorkHours daoWorkHours;

	/**
	 * Get doctor work hours. Except generic work hours (for days of week) also
	 * special events (holidays, free days, etc.) will be taken according to
	 * given time table.
	 * 
	 * @param doctorId
	 * @param dateFrom
	 * @param dateTo
	 * @param startRow
	 * @param rowCount
	 * @return
	 */
	@Secured
	public List<WorkHours> getDoctorWorkHours(int doctorId, Date dateFrom, Date dateTo, int startRow, int rowCount) {
		tracer(this).debug("Getting doctor's work hours - doctorId: " + doctorId + ", date from: " + dateFrom + ", date to: " + dateTo + ", startRow: " + startRow + ", rowCount: " + rowCount);
		return daoWorkHours.getDoctorWorkHours(doctorId, dateFrom, dateTo, startRow, rowCount);
	}

	/**
	 * Get doctor work hours count. Except generic work hours (for days of week)
	 * also special events (holidays, free days, etc.) will be counted according
	 * to given time table.
	 * 
	 * @param doctorId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@Secured
	public int getDoctorWorkHoursCount(int doctorId, Date dateFrom, Date dateTo) {
		tracer(this).debug("Getting doctor's work hours count - doctorId: " + doctorId + ", date from: " + dateFrom + ", date to: " + dateTo);
		return daoWorkHours.getDoctorWorkHoursCount(doctorId, dateFrom, dateTo);
	}

	@Secured
	@Override
	public WorkHours get(int entityId) throws ServerException {
		return super.get(entityId);
	}

	@Secured
	@Override
	public int getCount() throws ServerException {
		return super.getCount();
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public int create(WorkHours entity) throws ServerException {
		return super.create(entity);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public void delete(int entityId) throws ServerException {
		super.delete(entityId);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public void update(WorkHours entity) throws ServerException {
		super.update(entity);
	}

	@Override
	protected DaoEntityAccess<WorkHours> getDao() {
		return daoWorkHours;
	}

	public void setDaoWorkHours(DaoWorkHours daoWorkHours) {
		this.daoWorkHours = daoWorkHours;
	}

}
