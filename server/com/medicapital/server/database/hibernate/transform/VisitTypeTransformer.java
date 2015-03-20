package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.VisitType;
import com.medicapital.server.database.hibernate.entities.VisitTypeEntity;

public class VisitTypeTransformer implements Transformer<VisitType, VisitTypeEntity> {

	@Override
	public VisitTypeEntity createEntity(int entityId) {
		VisitTypeEntity entity = new VisitTypeEntity();
		entity.setVisitTypeId(entityId);
	    return entity;
	}
	
	@Override
	public VisitType createPojo(VisitTypeEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		VisitType visitType = new VisitType();
		visitType.setId(persistenceEntity.getVisitTypeId());
		visitType.setDoctorId(persistenceEntity.getDoctorId());
		visitType.setName(persistenceEntity.getName());
		visitType.setDescription(persistenceEntity.getDescription());
		visitType.setDuration(persistenceEntity.getDuration());
		return visitType;
	}

	@Override
	public VisitTypeEntity createEntity(VisitType pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		VisitTypeEntity visitTypeEntity = new VisitTypeEntity();
		visitTypeEntity.setVisitTypeId(pojoEntity.getId());
		visitTypeEntity.setDoctorId(pojoEntity.getDoctorId());
		visitTypeEntity.setName(pojoEntity.getName());
		visitTypeEntity.setDescription(pojoEntity.getDescription());
		visitTypeEntity.setDuration(pojoEntity.getDuration());
		return visitTypeEntity;
	}

}
