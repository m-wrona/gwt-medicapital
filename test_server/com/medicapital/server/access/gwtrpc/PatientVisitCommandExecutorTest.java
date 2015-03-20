package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.commands.visit.LockVisitCommand;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCommand;
import com.medicapital.common.commands.visit.SelectDoctorPatientVisitsCountCommand;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.PatientVisitCommandExecutor;
import com.medicapital.server.logic.PatientVisitFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class PatientVisitCommandExecutorTest {

	private PatientVisitFacade patientVisitFacade;
	private PatientVisitCommandExecutor visitCommandExecutor;

	@Before
	public void init() {
		patientVisitFacade = mock(PatientVisitFacade.class);
		visitCommandExecutor = new PatientVisitCommandExecutor();
		visitCommandExecutor.setPatientVisitFacade(patientVisitFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		visitCommandExecutor.setSessionFactory(sessionFactory);
		visitCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testHandleSelectCountCommand() {
		when(patientVisitFacade.getPatientVisitsCount("wrona")).thenReturn(3);
		final SelectCountCommand<PatientVisit> selectVisitCount = new SelectCountCommand<PatientVisit>(PatientVisit.class, "wrona");
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisitCount));
		verify(patientVisitFacade).getPatientVisitsCount("wrona");
		assertNotNull(serverResponse);
		final SelectCountCommandResp<PatientVisit> selectCountCommandResp = (SelectCountCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(3, selectCountCommandResp.getCount());
	}

	@Test
	public void testDoctorPatientVisitsCount() {
		when(patientVisitFacade.getDoctorPatientVisitsCount(1)).thenReturn(3);
		final SelectDoctorPatientVisitsCountCommand selectVisitCount = new SelectDoctorPatientVisitsCountCommand(1);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisitCount));
		verify(patientVisitFacade).getDoctorPatientVisitsCount(1);
		assertNotNull(serverResponse);
		final SelectCountCommandResp<PatientVisit> selectCountCommandResp = (SelectCountCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(3, selectCountCommandResp.getCount());
	}

	@Test
	public void testHandleSelectCommandById() {
		PatientVisit mockVisit = new PatientVisit();
		mockVisit.setId(1);
		when(patientVisitFacade.get(1)).thenReturn(mockVisit);
		final SelectCommand<PatientVisit> selectVisit = new SelectCommand<PatientVisit>(PatientVisit.class, 1);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisit));
		assertNotNull(serverResponse);
		final SelectCommandResp<PatientVisit> selectCommandResp = (SelectCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, selectCommandResp.getCount());
		final PatientVisit visit = selectCommandResp.getData().iterator().next();
		assertEquals(1, visit.getId());
	}

	@Test
	public void testHandleSelectCommandByLogin() {
		Set<PatientVisit> visits = new HashSet<PatientVisit>();
		visits.add(new PatientVisit());
		when(patientVisitFacade.getPatientVisits("wrona", 0, 10)).thenReturn(visits);
		final SelectCommand<PatientVisit> selectVisit = new SelectCommand<PatientVisit>(PatientVisit.class, "wrona");
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisit));
		assertNotNull(serverResponse);
		final SelectCommandResp<PatientVisit> selectCommandResp = (SelectCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, selectCommandResp.getCount());
	}

	@Test
	public void testHandleSelectDoctorVisitCommandByDate() {
		Set<PatientVisit> visits = new HashSet<PatientVisit>();
		visits.add(new PatientVisit());
		when(patientVisitFacade.getDoctorVisits(anyInt(), any(Date.class), any(Date.class))).thenReturn(visits);
		final SelectDoctorPatientVisitsCommand selectVisit = new SelectDoctorPatientVisitsCommand();
		selectVisit.setDoctorId(1);
		final DateManager dateManager = DateFactory.createDateManager();
		final Date beginVisitDate = new Date();
		dateManager.setDate(beginVisitDate, 2010, 3, 27);
		selectVisit.setBeginDate(beginVisitDate);
		final Date endVisitDate = new Date();
		dateManager.setDate(endVisitDate, 2010, 3, 28);
		selectVisit.setEndDate(endVisitDate);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisit));
		assertNotNull(serverResponse);
		final SelectCommandResp<PatientVisit> selectCommandResp = (SelectCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, selectCommandResp.getCount());
	}

	@Test
	public void testHandleSelectDoctorVisitCommand() {
		Set<PatientVisit> visits = new HashSet<PatientVisit>();
		visits.add(new PatientVisit());
		when(patientVisitFacade.getDoctorVisits(anyInt(), anyInt(), anyInt())).thenReturn(visits);
		final SelectDoctorPatientVisitsCommand selectVisit = new SelectDoctorPatientVisitsCommand();
		selectVisit.setDoctorId(1);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(selectVisit));
		assertNotNull(serverResponse);
		final SelectCommandResp<PatientVisit> selectCommandResp = (SelectCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, selectCommandResp.getCount());
	}

	@Test
	public void testHandleUpdateCommand() {
		final PatientVisit visit = new PatientVisit();
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2020, 1, 1);
		final UpdateCommand<PatientVisit> updateCommand = new UpdateCommand<PatientVisit>(PatientVisit.class, visit);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(updateCommand));
		verify(patientVisitFacade).update(visit);
		assertNotNull(serverResponse);
		final UpdateCommandResp<PatientVisit> updateCommandResp = (UpdateCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, updateCommandResp.getCount());
	}

	@Test
	public void testHandleCreateCommand() {
		final PatientVisit visit = new PatientVisit();
		when(patientVisitFacade.create(visit)).thenReturn(1);
		final CreateCommand<PatientVisit> createCommand = new CreateCommand<PatientVisit>(PatientVisit.class, visit);
		final ServerResponse<PatientVisit> serverResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(createCommand));
		verify(patientVisitFacade).create(visit);
		assertNotNull(serverResponse);
		final CreateCommandResp<PatientVisit> createCommandResp = (CreateCommandResp<PatientVisit>) serverResponse.getResponse();
		assertEquals(1, createCommandResp.getCreatedEntityId());
	}

	@Test
	public void testHandleDeleteCommand() {
		final PatientVisit visit = new PatientVisit();
		visit.setStartTime(new Date());
		DateFactory.createDateManager().setDate(visit.getStartTime(), 2020, 1, 1);
		when(patientVisitFacade.get(1)).thenReturn(visit);
		final DeleteCommand<PatientVisit> deleteCommand = new DeleteCommand<PatientVisit>(PatientVisit.class, 1);
		final ServerResponse<PatientVisit> deleteServerResponse = visitCommandExecutor.execute(new ServerRequest<PatientVisit>(deleteCommand));
		verify(patientVisitFacade).delete(1);
		assertNotNull(deleteServerResponse);
		final DeleteCommandResp<PatientVisit> deleteCommandResp = (DeleteCommandResp<PatientVisit>) deleteServerResponse.getResponse();
		assertEquals(1, deleteCommandResp.getCount());
	}

	@Test
	public void testHandleLockVisitCommand() {
		when(patientVisitFacade.getPatientVisitsCount("wrona")).thenReturn(3);
		final LockVisitCommand bookVisitCommand = new LockVisitCommand();
		visitCommandExecutor.handleCommand(bookVisitCommand);
		verify(patientVisitFacade).lockVisit(anyInt(), anyInt(), anyInt(), any(Date.class), any(Date.class), anyString());
	}
}
