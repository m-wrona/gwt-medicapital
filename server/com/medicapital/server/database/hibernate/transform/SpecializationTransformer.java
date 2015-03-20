package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.Specialization;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;

final public class SpecializationTransformer implements Transformer<Specialization, SpecializationEntity> {

	@Override
	public SpecializationEntity createEntity(int entityId) {
		SpecializationEntity entity = new SpecializationEntity();
		entity.setSpecializationId(entityId);
	    return entity;
	}
	
	@Override
	public Specialization createPojo(final SpecializationEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		final Specialization specialization = new Specialization();
		specialization.setId(persistenceEntity.getSpecializationId());
		specialization.setName(persistenceEntity.getName());
		return specialization;
	}

	@Override
	public SpecializationEntity createEntity(final Specialization pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		final SpecializationEntity specializationEntity = new SpecializationEntity();
		specializationEntity.setSpecializationId(pojoEntity.getId());
		specializationEntity.setName(pojoEntity.getName());
		return specializationEntity;
	}

}
