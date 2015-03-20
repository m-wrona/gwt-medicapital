package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.server.database.hibernate.HibernateDaoUtil;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;

public class DoctorTransformer implements Transformer<Doctor, DoctorEntity> {

	private final UserTransformer userTransformer = new UserTransformer();
	private final SpecializationTransformer specializationTransformer = new SpecializationTransformer();

	@Override
	public DoctorEntity createEntity(int entityId) {
		DoctorEntity entity = new DoctorEntity();
		entity.setDoctorId(entityId);
	    return entity;
	}
	
	@Override
	public Doctor createPojo(final DoctorEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		final Doctor doctor = new Doctor();
		doctor.setId(persistenceEntity.getDoctorId());
		doctor.setWorkId(persistenceEntity.getWorkId());
		doctor.setNotes(persistenceEntity.getNotes());
		doctor.setAverageEvaluation(persistenceEntity.getAverageEvaluation());
		doctor.setUser(userTransformer.createPojo(persistenceEntity.getUserEntity()));
		doctor.setSpecializations(HibernateDaoUtil.toPojoSet(persistenceEntity.getSpecializations(), specializationTransformer));
		return doctor;
	}

	@Override
	public DoctorEntity createEntity(final Doctor pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		final DoctorEntity doctorEntity = new DoctorEntity();
		doctorEntity.setDoctorId(pojoEntity.getId());
		doctorEntity.setWorkId(pojoEntity.getWorkId());
		doctorEntity.setNotes(pojoEntity.getNotes());
		doctorEntity.setAverageEvaluation(pojoEntity.getAverageEvaluation());
		doctorEntity.setUserEntity(userTransformer.createEntity(pojoEntity.getUser()));
		doctorEntity.setSpecializations(HibernateDaoUtil.toEntitySet(pojoEntity.getSpecializations(), specializationTransformer));
		return doctorEntity;
	}
	
	public DoctorEntity createEntity(SearchDoctorCriteria searchDoctorCriteria) {
		final DoctorEntity doctorEntity = new DoctorEntity();
		doctorEntity.setUserEntity(userTransformer.createEntity(searchDoctorCriteria));
		return doctorEntity;
	}

}
