package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.Localization;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;

public class LocalizationTransformer implements Transformer<Localization, LocalizationEntity> {

	@Override
	public LocalizationEntity createEntity(int entityId) {
		LocalizationEntity entity = new LocalizationEntity();
		entity.setLocalizationId(entityId);
		return entity;
	}

	@Override
	public Localization createPojo(LocalizationEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		Localization localization = new Localization();
		localization.setId(persistenceEntity.getLocalizationId());
		localization.setAddress(persistenceEntity.getAddress());
		localization.setPostalCode(persistenceEntity.getPostalCode());
		localization.setCityId(persistenceEntity.getCityId());
		return localization;
	}

	@Override
	public LocalizationEntity createEntity(Localization pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		LocalizationEntity localization = new LocalizationEntity();
		localization.setLocalizationId(pojoEntity.getId());
		localization.setAddress(pojoEntity.getAddress());
		localization.setPostalCode(pojoEntity.getPostalCode());
		localization.setCityId(pojoEntity.getCityId());
		return localization;
	}

}
