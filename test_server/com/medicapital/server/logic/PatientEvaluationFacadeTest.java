package com.medicapital.server.logic;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.database.DaoPatientEvaluation;

public class PatientEvaluationFacadeTest {

	private DaoPatientEvaluation daoPatientEvaluation;
	private PatientEvaluationFacade patientEvaluationFacade;

	@Before
	public void init() {
		daoPatientEvaluation = mock(DaoPatientEvaluation.class);
		patientEvaluationFacade = new PatientEvaluationFacade();
		patientEvaluationFacade.setDaoPatientEvaluation(daoPatientEvaluation);
	}

	@Test
	public void testGetDoctorEvaluationsCount() {
		patientEvaluationFacade.getDoctorEvaluationsCount(1);
		verify(daoPatientEvaluation).getDoctorEvaluationsCount(1);
	}

	@Test
	public void testGetDoctorPatientEvaluations() {
		patientEvaluationFacade.getDoctorPatientEvaluations(1, 0, 10);
		verify(daoPatientEvaluation).getDoctorPatientEvaluations(1, 0, 10);
	}

	@Test(expected = ServerException.class)
	public void testDelete() {
		patientEvaluationFacade.delete(1);
	}

	@Test(expected = ServerException.class)
	public void testUpdate() {
		patientEvaluationFacade.update(new PatientEvaluation());
	}

}
