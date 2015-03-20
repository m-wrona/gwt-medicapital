package com.medicapital.server.logic;

import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.security.Secured;

public class CalendarEventFacade extends EntityFacade<CalendarEvent> {

	private PatientVisitFacade patientVisitFacade;

	@Secured
	@Override
	public void update(CalendarEvent entity) throws ServerException {
		if (entity instanceof PatientVisit) {
			patientVisitFacade.update((PatientVisit) entity);
		} else {
			super.update(entity);
		}
	}

	@Secured
	@Override
	public int create(CalendarEvent entity) throws ServerException {
		if (entity instanceof PatientVisit) {
			return patientVisitFacade.create((PatientVisit) entity);
		} else {
			return super.create(entity);
		}
	}

	@Secured
	@Override
	public void delete(int entityId) throws ServerException {
		super.delete(entityId);
	}

	@Secured
	public void deleteEvent(int eventId, Class<?> eventClass) {
		if (eventClass == PatientVisit.class) {
			patientVisitFacade.delete(eventId);
		} else if (eventClass == CalendarEvent.class) {
			delete(eventId);
		}
	}

	@Override
	protected DaoEntityAccess<CalendarEvent> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPatientVisitFacade(PatientVisitFacade patientVisitFacade) {
		this.patientVisitFacade = patientVisitFacade;
	}

	public PatientVisitFacade getPatientVisitFacade() {
		return patientVisitFacade;
	}

}
