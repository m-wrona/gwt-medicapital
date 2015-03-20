package com.medicapital.server.database.hibernate.transform;

import java.util.Date;
import com.medicapital.common.entities.PatientEvaluation;
import com.medicapital.server.database.hibernate.entities.PatientEvaluationEntity;

final public class PatientEvaluationTransformer implements Transformer<PatientEvaluation, PatientEvaluationEntity> {

	@Override
	public PatientEvaluationEntity createEntity(int entityId) {
		PatientEvaluationEntity entity = new PatientEvaluationEntity();
		entity.setEvaluationId(entityId);
		return entity;
	}

	@Override
	public PatientEvaluation createPojo(final PatientEvaluationEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		final PatientEvaluation evaluation = new PatientEvaluation();
		evaluation.setId(persistenceEntity.getEvaluationId());
		evaluation.setTitle(persistenceEntity.getTitle());
		evaluation.setDescription(persistenceEntity.getDescription());
		evaluation.setEvaluation(persistenceEntity.getEvaluation());
		if (persistenceEntity.getCreated() != null) {
			evaluation.setCreated(new Date(persistenceEntity.getCreated().getTime()));
		}
		return evaluation;
	}

	@Override
	public PatientEvaluationEntity createEntity(final PatientEvaluation pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		final PatientEvaluationEntity evaluationEntity = new PatientEvaluationEntity();
		evaluationEntity.setEvaluationId(pojoEntity.getId());
		evaluationEntity.setTitle(pojoEntity.getTitle());
		evaluationEntity.setDescription(pojoEntity.getDescription());
		evaluationEntity.setEvaluation(pojoEntity.getEvaluation());
		evaluationEntity.setCreated(pojoEntity.getCreated());
		return evaluationEntity;
	}

}
