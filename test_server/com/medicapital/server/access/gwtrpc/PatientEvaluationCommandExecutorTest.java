package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCommand;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCountCommand;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.PatientEvaluationCommandExecutor;
import com.medicapital.server.logic.PatientEvaluationFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class PatientEvaluationCommandExecutorTest {

	private PatientEvaluationCommandExecutor commandExecutor;
	private PatientEvaluationFacade patientEvaluationFacade;

	@Before
	public void init() {
		patientEvaluationFacade = mock(PatientEvaluationFacade.class);
		commandExecutor = new PatientEvaluationCommandExecutor();
		commandExecutor.setPatientEvaluationFacade(patientEvaluationFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		commandExecutor.setSessionFactory(sessionFactory);
		commandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testHandleSelectCountEvaluationCommand() {
		when(patientEvaluationFacade.getDoctorEvaluationsCount(1)).thenReturn(10);
		final SelectDoctorEvaluationCountCommand createCommand = new SelectDoctorEvaluationCountCommand(1);
		final ServerResponse<PatientEvaluation> serverResponse = commandExecutor.execute(new ServerRequest<PatientEvaluation>(createCommand));
		verify(patientEvaluationFacade).getDoctorEvaluationsCount(1);
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		final SelectCountCommandResp<PatientEvaluation> commandResp = (SelectCountCommandResp<PatientEvaluation>) serverResponse.getResponse();
		assertEquals(10, commandResp.getCount());
	}
	
	@Test
	public void testHandleSelectEvaluationCommand() {
		Set<PatientEvaluation> evaluations = new HashSet<PatientEvaluation>();
		evaluations.add(new PatientEvaluation());
		when(patientEvaluationFacade.getDoctorPatientEvaluations(1, 0, 10)).thenReturn(evaluations);
		final SelectDoctorEvaluationCommand createCommand = new SelectDoctorEvaluationCommand(1);
		final ServerResponse<PatientEvaluation> serverResponse = commandExecutor.execute(new ServerRequest<PatientEvaluation>(createCommand));
		verify(patientEvaluationFacade).getDoctorPatientEvaluations(1, 0, 10);
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		final SelectCommandResp<PatientEvaluation> commandResp = (SelectCommandResp<PatientEvaluation>) serverResponse.getResponse();
		assertEquals(1, commandResp.getCount());
	}
	
	@Test
	public void testHandleCreateEvaluationCommand() {
		final PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setVisitId(1);
		evaluation.setTitle("Super lekarz");
		evaluation.setDescription("Polecam tego lekarza");
		evaluation.setCreated(new Date());
		when(patientEvaluationFacade.create(evaluation)).thenReturn(1);
		final CreateCommand<PatientEvaluation> createCommand = new CreateCommand<PatientEvaluation>(PatientEvaluation.class, evaluation);
		final ServerResponse<PatientEvaluation> serverResponse = commandExecutor.execute(new ServerRequest<PatientEvaluation>(createCommand));
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		final CreateCommandResp<PatientEvaluation> commandResp = (CreateCommandResp<PatientEvaluation>) serverResponse.getResponse();
		assertEquals(1, commandResp.getCreatedEntityId());
	}

}
