package com.medicapital.server.database.hibernate.criteria;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import com.medicapital.server.database.hibernate.entities.UserEntity;

final public class UserEntityCriteria {

	private final LocalizationEntityCriteria localizationCriteria = new LocalizationEntityCriteria();

	final public DetachedCriteria createSelectCount(final UserEntity userSearchCriteria) {
		final DetachedCriteria selectCountCriteria = createSelect(userSearchCriteria);
		selectCountCriteria.setProjection(Projections.countDistinct("userId"));
		return selectCountCriteria;
	}
	
	/**
	 * Create criteria for user search
	 * 
	 * @param userSearchCriteria
	 * @return
	 */
	final public DetachedCriteria createSelect(final UserEntity userSearchCriteria) {
		return DetachedCriteria.forClass(UserEntity.class).add(createExample(userSearchCriteria));
	}

	/**
	 * Create sub-criteria for user search
	 * 
	 * @param criteria
	 * @param criteriaName
	 * @param userEntity
	 * @return
	 */
	final public DetachedCriteria createSubSelect(final DetachedCriteria criteria, final String criteriaName, final UserEntity userEntity) {
		final DetachedCriteria userCriteria = criteria.createCriteria(criteriaName, CriteriaSpecification.LEFT_JOIN);
		userCriteria.add(createExample(userEntity));
		if (userEntity.getLocalization() != null) {
			localizationCriteria.createSubSelect(userCriteria, "localization", userEntity.getLocalization());
		}
		return userCriteria;
	}

	private Example createExample(final UserEntity userSearchCriteria) {
		final Example userExample = Example.create(userSearchCriteria);
		userExample.enableLike(MatchMode.ANYWHERE);
		userExample.ignoreCase();
		userExample.setPropertySelector(new NotNullPropertySelector());
		return userExample;
	}

}
