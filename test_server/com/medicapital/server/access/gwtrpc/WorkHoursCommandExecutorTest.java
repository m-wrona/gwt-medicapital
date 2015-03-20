package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.medicapital.common.commands.doctor.work.SelectWorkHoursCommand;
import com.medicapital.common.commands.doctor.work.SelectWorkHoursCountCommand;
import com.medicapital.server.logic.WorkHoursFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class WorkHoursCommandExecutorTest {

	private WorkHoursFacade workHoursFacade;
	private WorkHoursCommandExecutor workHoursCommandExecutor;

	@Before
	public void init() {
		workHoursFacade = mock(WorkHoursFacade.class);
		workHoursCommandExecutor = new WorkHoursCommandExecutor();
		workHoursCommandExecutor.setWorkHoursFacade(workHoursFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		workHoursCommandExecutor.setSessionFactory(sessionFactory);
		workHoursCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testHandleSelectWorkHoursCommand() {
		Date date = new Date();
		SelectWorkHoursCommand selectCommand = new SelectWorkHoursCommand();
		selectCommand.setDoctorId(1);
		selectCommand.setDateFrom(date);
		selectCommand.setDateTo(date);
		workHoursCommandExecutor.handleCommand(selectCommand);
		verify(workHoursFacade).getDoctorWorkHours(1, date, date, 0, 10);
	}

	@Test
	public void testHandleSelectWorkHoursCountCommand() {
		Date date = new Date();
		SelectWorkHoursCountCommand selectCountCommand = new SelectWorkHoursCountCommand();
		selectCountCommand.setDoctorId(1);
		selectCountCommand.setDateFrom(date);
		selectCountCommand.setDateTo(date);
		workHoursCommandExecutor.handleCommand(selectCountCommand);
		verify(workHoursFacade).getDoctorWorkHoursCount(1, date, date);
	}
}
