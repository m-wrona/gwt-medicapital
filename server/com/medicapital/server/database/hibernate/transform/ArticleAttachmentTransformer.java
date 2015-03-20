package com.medicapital.server.database.hibernate.transform;

import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.server.database.hibernate.entities.ArticleAttachmentsEntity;

public class ArticleAttachmentTransformer implements Transformer<UrlResource, ArticleAttachmentsEntity> {

	@Override
	public ArticleAttachmentsEntity createEntity(int entityId) {
		ArticleAttachmentsEntity entity = new ArticleAttachmentsEntity();
		entity.setAttachmentId(entityId);
		return entity;
	}

	@Override
	public UrlResource createPojo(ArticleAttachmentsEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		UrlResource resource = new UrlResource();
		resource.setId(persistenceEntity.getAttachmentId());
		resource.setDescription(persistenceEntity.getDescription());
		resource.setUrlResourceType(UrlResourceType.getType(persistenceEntity.getType()));
		return resource;
	}

	@Override
	public ArticleAttachmentsEntity createEntity(UrlResource pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		ArticleAttachmentsEntity attachment = new ArticleAttachmentsEntity();
		attachment.setAttachmentId(pojoEntity.getId());
		attachment.setDescription(pojoEntity.getDescription());
		attachment.setType(pojoEntity.getUrlResourceType().toString());
		return attachment;
	}

}
