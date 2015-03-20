package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.Hobby;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;

public class HobbyTransformer implements Transformer<Hobby, HobbyEntity> {

	@Override
	public HobbyEntity createEntity(int entityId) {
		HobbyEntity entity = new HobbyEntity();
		entity.setHobbyId(entityId);
		return entity;
	}

	@Override
	public Hobby createPojo(HobbyEntity persistenceEntity) {
		Hobby hobby = new Hobby();
		hobby.setId(persistenceEntity.getHobbyId());
		hobby.setName(persistenceEntity.getName());
		return hobby;
	}

	@Override
	public HobbyEntity createEntity(Hobby pojoEntity) {
		HobbyEntity hobbyEntity = new HobbyEntity();
		hobbyEntity.setHobbyId(pojoEntity.getId());
		hobbyEntity.setName(pojoEntity.getName());
		return hobbyEntity;
	}

}
