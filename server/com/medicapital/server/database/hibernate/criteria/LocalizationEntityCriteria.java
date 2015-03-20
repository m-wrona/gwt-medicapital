package com.medicapital.server.database.hibernate.criteria;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;

final public class LocalizationEntityCriteria {

	final public DetachedCriteria createSubSelect(final DetachedCriteria criteria, final String criteriaName, final LocalizationEntity localizationEntity) {
		final DetachedCriteria localizationCriteria = criteria.createCriteria(criteriaName);
		localizationCriteria.add(createExample(localizationEntity));
		return localizationCriteria;
	}

	final private Example createExample(final LocalizationEntity localizationEntity) {
		final Example localizationExample = Example.create(localizationEntity);
		localizationExample.enableLike(MatchMode.ANYWHERE);
		localizationExample.excludeZeroes();
		localizationExample.ignoreCase();
		return localizationExample;
	}

}
