package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Date;
import java.util.Set;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DaoPatientVisit;
import com.medicapital.server.lang.Lang;
import com.medicapital.server.security.Secured;

public class PatientVisitFacade extends EntityFacade<PatientVisit> {

	private DaoPatientVisit daoPatientVisit;

	/**
	 * Create/update lock for a visit
	 * 
	 * @param visitId
	 *            ID of visit (if update operation)
	 * @param patientId
	 * @param doctorId
	 * @param visitStartTime
	 * @param visitEndTime
	 * @param visitTitle
	 * @return
	 * @throws ServerException
	 */
	@Secured
	public PatientVisit lockVisit(int visitId, int patientId, int doctorId, Date visitStartTime, Date visitEndTime, String visitTitle) throws ServerException {
		int lockVisitId = lockVisit(visitId, patientId, doctorId, visitStartTime, visitEndTime, visitTitle, new Date());
		return get(lockVisitId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private int lockVisit(int visitId, int patientId, int doctorId, Date visitStartTime, Date visitEndTime, String visitTitle, Date lockTime) {
		tracer(this).debug("Locking visit - visitId: " + visitId + " patientId: " + patientId + ", doctorId: " + doctorId + ", start time: " + visitStartTime + ", end time: " + visitEndTime + ", visit title: " + visitTitle + ", lock time: " + lockTime);
		if (patientId < 1) {
			tracer(this).debug("Locking visit: patientId is unknown - assuming doctorId will be temp patientId");
			patientId = doctorId;
		}
		if (visitId > 0) {
			if (daoPatientVisit.isVisitTermExpendable(doctorId, visitStartTime, visitEndTime)) {
				daoPatientVisit.updateLockedVisit(visitId, visitStartTime, visitEndTime, lockTime, visitTitle);
			} else {
				visitId = 0;
			}
		} else if (daoPatientVisit.isVisitTermFree(doctorId, visitStartTime, visitEndTime)) {
			visitId = daoPatientVisit.lockVisit(patientId, doctorId, visitStartTime, visitEndTime, lockTime, visitTitle);
		}
		if (visitId == 0) {
			ServerException noTermAvailableException = new ServerException("Couldn't book visit because no available terms were found");
			noTermAvailableException.setClientMessage(Lang.warning().givenTermNotAvailable());
			throw noTermAvailableException;
		}
		return visitId;
	}

	@Secured
	public int getDoctorPatientVisitsCount(int doctorId) {
		return daoPatientVisit.getDoctorPatientVisitsCount(doctorId);
	}

	@Secured
	public int getPatientVisitsCount(String login) {
		return daoPatientVisit.getPatientVisitsCount(login);
	}

	@Secured
	public Set<PatientVisit> getPatientVisits(String login, int startRow, int rowCount) {
		return daoPatientVisit.getPatientVisits(login, startRow, rowCount);
	}

	@Secured
	public Set<PatientVisit> getDoctorVisits(int doctorId, Date beginDate, Date endDate) {
		return daoPatientVisit.getDoctorVisits(doctorId, beginDate, endDate);
	}

	@Secured
	public Set<PatientVisit> getDoctorVisits(int doctorId, int startRow, int rowCount) {
		return daoPatientVisit.getDoctorVisits(doctorId, startRow, rowCount);
	}

	@Secured
	@Override
	public int create(PatientVisit entity) throws ServerException {
		return super.create(entity);
	}

	@Secured
	@Override
	public void update(PatientVisit entity) throws ServerException {
		final DateManager dateManager = DateFactory.createDateManager();
		if (dateManager.after(new Date(), entity.getStartTime())) {
			final ServerException notEditableVisitEx = new ServerException("Visit " + entity.getId() + " can't be updated because visit has already had place");
			notEditableVisitEx.setClientMessage(Lang.warning().eventNotEditable());
			throw notEditableVisitEx;
		}
		super.update(entity);
	}

	@Secured
	@Override
	public void delete(int entityId) throws ServerException {
		final DateManager dateManager = DateFactory.createDateManager();
		PatientVisit visit = get(entityId);
		if (visit == null) {
			return;
		} else if (dateManager.after(new Date(), visit.getStartTime())) {
			final ServerException notEditableVisitEx = new ServerException("Visit " + visit.getId() + " can't be deleted because visit has already had place");
			notEditableVisitEx.setClientMessage(Lang.warning().eventNotEditable());
			throw notEditableVisitEx;
		}
		super.delete(entityId);
	}

	@Override
	final protected DaoEntityAccess<PatientVisit> getDao() {
		return daoPatientVisit;
	}

	public void setDaoPatientVisit(DaoPatientVisit daoPatientVisit) {
		this.daoPatientVisit = daoPatientVisit;
	}

}
