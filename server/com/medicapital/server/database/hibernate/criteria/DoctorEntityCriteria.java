package com.medicapital.server.database.hibernate.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.transform.DoctorTransformer;

final public class DoctorEntityCriteria {

	public Example createExample(final DoctorEntity doctorSearchCriteria) {
		final Example doctorExample = Example.create(doctorSearchCriteria);
		doctorExample.enableLike(MatchMode.ANYWHERE);
		doctorExample.ignoreCase();
		doctorExample.setPropertySelector(new NotNullPropertySelector());
		return doctorExample;
	}

	final public DetachedCriteria createSelect(final DoctorEntity doctorEntity) {
		final DetachedCriteria doctorCriteria = DetachedCriteria.forClass(DoctorEntity.class);
		doctorCriteria.add(createExample(doctorEntity));
		if (doctorEntity.getUserEntity() != null) {
			UserEntityCriteria userEntityCriteria = new UserEntityCriteria();
			userEntityCriteria.createSubSelect(doctorCriteria, "userEntity", doctorEntity.getUserEntity());
		}
		doctorCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return doctorCriteria;
	}

	/**
	 * Create criteria for doctor search
	 * 
	 * @param userSearchCriteria
	 * @return
	 */
	final public DetachedCriteria createSelect(final SearchDoctorCriteria doctorSearchCriteria) {
		DoctorEntity doctorEntity = new DoctorTransformer().createEntity(doctorSearchCriteria);
		final DetachedCriteria doctorCriteria = createSelect(doctorEntity);
		if (doctorSearchCriteria.getEvaluationFrom() > 0 && doctorSearchCriteria.getEvaluationTo() > 0) {
			doctorCriteria.add(Restrictions.and(Restrictions.ge("averageEvaluation", doctorSearchCriteria.getEvaluationFrom()), Restrictions.le("averageEvaluation", doctorSearchCriteria.getEvaluationTo())));
		}
		return doctorCriteria;
	}

	/**
	 * Create criteria for select doctor count
	 * 
	 * @param doctorSearchCriteria
	 * @return
	 */
	final public DetachedCriteria createSelectCount(final SearchDoctorCriteria doctorSearchCriteria) {
		final DetachedCriteria selectCountCriteria = createSelect(doctorSearchCriteria);
		selectCountCriteria.setProjection(Projections.countDistinct("doctorId"));
		return selectCountCriteria;
	}

}
