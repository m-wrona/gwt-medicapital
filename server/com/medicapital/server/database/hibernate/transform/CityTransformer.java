package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.City;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;

public class CityTransformer implements Transformer<City, CityEntity> {

	@Override
	public CityEntity createEntity(int entityId) {
		CityEntity entity = new CityEntity();
		entity.setCityId(entityId);
		return entity;
	}

	@Override
	public City createPojo(CityEntity persistenceEntity) {
		City city = new City();
		city.setId(persistenceEntity.getCityId());
		city.setName(persistenceEntity.getName());
		city.setRegionId(persistenceEntity.getRegion().getRegionId());
		city.setRegionName(persistenceEntity.getRegion().getName());
		return city;
	}

	@Override
	public CityEntity createEntity(City pojoEntity) {
		CityEntity cityEntity = new CityEntity();
		cityEntity.setCityId(pojoEntity.getId());
		cityEntity.setName(pojoEntity.getName());
		RegionEntity regionEntity = new RegionEntity();
		cityEntity.setRegion(regionEntity);
		regionEntity.setRegionId(pojoEntity.getRegionId());
		regionEntity.setName(pojoEntity.getRegionName());
		return cityEntity;
	}

}
