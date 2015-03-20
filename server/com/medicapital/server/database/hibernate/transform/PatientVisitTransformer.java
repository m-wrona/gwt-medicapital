package com.medicapital.server.database.hibernate.transform;

import java.util.Date;

import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.PatientVisitEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class PatientVisitTransformer implements Transformer<PatientVisit, PatientVisitEntity> {

	@Override
	public PatientVisitEntity createEntity(int entityId) {
		PatientVisitEntity entity = new PatientVisitEntity();
		entity.setVisitId(entityId);
		return entity;
	}

	@Override
	public PatientVisit createPojo(final PatientVisitEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		final PatientVisit visit = new PatientVisit();
		visit.setId(persistenceEntity.getVisitId());
		visit.setTitle(persistenceEntity.getTitle());
		visit.setDescription(persistenceEntity.getNotes());
		if (persistenceEntity.getBegin() != null) {
			visit.setStartTime(new Date(persistenceEntity.getBegin().getTime()));
		}
		if (persistenceEntity.getEnd() != null) {
			visit.setEndTime(new Date(persistenceEntity.getEnd().getTime()));
		}
		if (persistenceEntity.getLockTime() != null) {
			visit.setLockTime(new Date(persistenceEntity.getLockTime().getTime()));
		}
		// attach localization
		final LocalizationTransformer localizationTransformer = new LocalizationTransformer();
		visit.setLocalization(localizationTransformer.createPojo(persistenceEntity.getLocalization()));

		// attach patient data
		final User patient = new User();
		visit.setPatient(patient);
		patient.setId(persistenceEntity.getPatient().getUserId());
		patient.setLogin(persistenceEntity.getPatient().getLogin());
		patient.setFirstName(persistenceEntity.getPatient().getFirstName());
		patient.setLastName(persistenceEntity.getPatient().getLastName());

		// attach doctor data
		final Doctor doctor = new Doctor();
		visit.setDoctor(doctor);
		doctor.setId(persistenceEntity.getDoctor().getDoctorId());
		final User doctorUser = new User();
		doctor.setUser(doctorUser);
		doctorUser.setId(persistenceEntity.getDoctor().getUserEntity().getUserId());
		doctorUser.setLogin(persistenceEntity.getDoctor().getUserEntity().getLogin());
		doctorUser.setFirstName(persistenceEntity.getDoctor().getUserEntity().getFirstName());
		doctorUser.setLastName(persistenceEntity.getDoctor().getUserEntity().getLastName());
		doctorUser.setEmail(persistenceEntity.getDoctor().getUserEntity().getEmail());

		// attach evaluation
		final PatientEvaluationTransformer evaluationTransformer = new PatientEvaluationTransformer();
		visit.setEvaluation(evaluationTransformer.createPojo(persistenceEntity.getEvaluationEntity()));
		if (visit.getEvaluation() != null) {
			visit.getEvaluation().setVisitId(visit.getId());
		}

		return visit;
	}

	@Override
	public PatientVisitEntity createEntity(final PatientVisit pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		final PatientVisitEntity visitEntity = new PatientVisitEntity();
		visitEntity.setVisitId(pojoEntity.getId());
		visitEntity.setBegin(pojoEntity.getStartTime());
		visitEntity.setEnd(pojoEntity.getEndTime());
		visitEntity.setTitle(pojoEntity.getTitle());
		visitEntity.setNotes(pojoEntity.getDescription());
		visitEntity.setLockTime(pojoEntity.getLockTime());

		// attach patient
		final UserEntity patientEntity = new UserEntity();
		patientEntity.setUserId(pojoEntity.getPatient().getId());
		visitEntity.setPatient(patientEntity);

		// attach doctor
		final DoctorEntity doctorEntity = new DoctorEntity();
		doctorEntity.setDoctorId(pojoEntity.getDoctor().getId());
		visitEntity.setDoctor(doctorEntity);

		// attach localization
		final LocalizationTransformer localizationTransformer = new LocalizationTransformer();
		visitEntity.setLocalization(localizationTransformer.createEntity(pojoEntity.getLocalization()));

		// attach evaluation
		final PatientEvaluationTransformer evaluationTransformer = new PatientEvaluationTransformer();
		visitEntity.setEvaluationEntity(evaluationTransformer.createEntity(pojoEntity.getEvaluation()));

		return visitEntity;
	}

}
