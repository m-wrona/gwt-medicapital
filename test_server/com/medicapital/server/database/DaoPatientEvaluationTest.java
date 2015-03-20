package com.medicapital.server.database;

import java.util.Date;
import java.util.Set;
import org.junit.Test;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.database.hibernate.HibernatePatientEvaluation;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.PatientEvaluationEntity;
import com.medicapital.server.database.hibernate.entities.PatientVisitEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoPatientEvaluationTest extends AbstractDBTestCase {

	private HibernatePatientEvaluation daoPatientEvaluation;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(DoctorEntity.class);
		addAnnotatedClass(SpecializationEntity.class);
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		addAnnotatedClass(PatientVisitEntity.class);
		addAnnotatedClass(PatientEvaluationEntity.class);
		daoPatientEvaluation = new HibernatePatientEvaluation();
		daoPatientEvaluation.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoPatientEvaluation.close();
	}

	@Test
	public void testCreatePatientEvaluation() {
		final PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setVisitId(2);
		evaluation.setTitle("Super lekarz");
		evaluation.setDescription("Polecam tego lekarza");
		evaluation.setCreated(new Date());
		final int createdEvaluationId = daoPatientEvaluation.create(evaluation);
		assertTrue("ID of created evaluation unknown", createdEvaluationId > 0);
	}

	@Test
	public void testGetDoctorEvaluationCount() {
		assertEquals(1, daoPatientEvaluation.getDoctorEvaluationsCount(1));
		assertEquals(0, daoPatientEvaluation.getDoctorEvaluationsCount(2));
	}

	@Test
	public void testGetDoctorEvaluations() {
		final Set<PatientEvaluation> evaluations = daoPatientEvaluation.getDoctorPatientEvaluations(1, 0, 10);
		assertNotNull(evaluations);
		assertEquals(1, evaluations.size());
		final PatientEvaluation evaluation = evaluations.iterator().next();
		assertEquals(1, evaluation.getId());
	}
}
