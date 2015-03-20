package com.medicapital.server.access.gwtrpc;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Set;
import com.medicapital.common.commands.calendar.DeleteCalendarEventCommand;
import com.medicapital.common.commands.calendar.SelectCalendarEventCommand;
import com.medicapital.common.commands.calendar.SelectCalendarEventCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.CalendarEvent;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.server.logic.CalendarEventFacade;
import com.medicapital.server.logic.WorkHoursFacade;

public class CalendarEventCommandExecutor extends CommandExecutor<CalendarEvent> {

	private static final long serialVersionUID = -8004545006768046394L;
	private CalendarEventFacade calendarEventFacade;
	private WorkHoursFacade workHoursFacade;

	@Override
	protected SelectCommandResp<CalendarEvent> handleSelectCommand(SelectCommand<CalendarEvent> selectCommand) throws CommandExecutionException, ServerException {
		if (selectCommand instanceof SelectCalendarEventCommand) {
			return handleSelectVisitCommand((SelectCalendarEventCommand) selectCommand);
		}
		return super.handleSelectCommand(selectCommand);
	}

	private SelectCommandResp<CalendarEvent> handleSelectVisitCommand(final SelectCalendarEventCommand command) throws CommandExecutionException, ServerException {
		final SelectCalendarEventCommandResp resp = new SelectCalendarEventCommandResp();
		if (command.getDoctorId() > 0) {
			final Set<PatientVisit> visits = calendarEventFacade.getPatientVisitFacade().getDoctorVisits(command.getDoctorId(), command.getBeginDate(), command.getEndDate());
			resp.addAll(visits);
			resp.addWorkHours(workHoursFacade.getDoctorWorkHours(command.getDoctorId(), command.getBeginDate(), command.getEndDate(), 0, 50));
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected DeleteCommandResp<CalendarEvent> handleDeleteCommand(DeleteCommand<CalendarEvent> deleteCommand) throws CommandExecutionException, ServerException {
		try {
			if (deleteCommand instanceof DeleteCalendarEventCommand) {
				DeleteCalendarEventCommand deleteEventCommand = (DeleteCalendarEventCommand) deleteCommand;
				Class<? extends CalendarEvent> eventClass = (Class<? extends CalendarEvent>) Class.forName(((DeleteCalendarEventCommand) deleteCommand).getEventClassName());
				tracer(this).debug("Deleting event - class: " + eventClass + ", ids: " + deleteEventCommand.getElementIds());
				int deletedElements = 0;
				for (int eventId : deleteEventCommand.getElementIds()) {
					calendarEventFacade.deleteEvent(eventId, eventClass);
					deletedElements++;
				}
				return new DeleteCommandResp<CalendarEvent>(CalendarEvent.class, deletedElements);
			}
			return super.handleDeleteCommand(deleteCommand);
		} catch (ClassNotFoundException e) {
			throw new CommandExecutionException(e);
		}
	}

	public void setCalendarEventFacade(CalendarEventFacade calendarEventFacade) {
		setFacade(calendarEventFacade);
		this.calendarEventFacade = calendarEventFacade;
	}

	@Override
	protected Class<CalendarEvent> getEntityClass() {
		return CalendarEvent.class;
	}

	public void setWorkHoursFacade(WorkHoursFacade workHoursFacade) {
		this.workHoursFacade = workHoursFacade;
	}
}
