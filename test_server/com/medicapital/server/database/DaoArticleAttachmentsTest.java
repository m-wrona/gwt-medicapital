package com.medicapital.server.database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.server.database.hibernate.HibernateArticleAttachments;
import com.medicapital.server.database.hibernate.entities.ArticleAttachmentsEntity;
import com.medicapital.server.database.hibernate.entities.ArticleEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoArticleAttachmentsTest extends AbstractDBTestCase {

	private HibernateArticleAttachments daoArticleAttachments;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(ArticleEntity.class);
		addAnnotatedClass(ArticleAttachmentsEntity.class);
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		daoArticleAttachments = new HibernateArticleAttachments();
		daoArticleAttachments.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoArticleAttachments.close();
	}

	@Test
	public void testCreateArticleAttachment() {
		byte[] value = new byte[1];
		String description = "description";
		UrlResourceType urlResourceType = UrlResourceType.Image;
		int attachmentId = daoArticleAttachments.createArticleAttachment(1, value, description, urlResourceType);
		byte[] attachment = daoArticleAttachments.getArticleAttachment(1, attachmentId);
		assertNotNull(attachment);
		assertEquals(1, attachment.length);
	}

	@Test
	public void testDeleteArticleAttachment() {
		byte[] value = new byte[1];
		String description = "description";
		UrlResourceType urlResourceType = UrlResourceType.Image;
		int attachmentId = daoArticleAttachments.createArticleAttachment(1, value, description, urlResourceType);
		byte[] attachment = daoArticleAttachments.getArticleAttachment(1, attachmentId);
		assertNotNull(attachment);
		assertEquals(1, attachment.length);
		daoArticleAttachments.deleteArticleAttachment(1, attachmentId);
		assertNull(daoArticleAttachments.getArticleAttachment(1, attachmentId));
	}

	@Test
	public void testGetArticleAttachments() {
		byte[] value = new byte[1];
		String description = "description";
		UrlResourceType urlResourceType = UrlResourceType.Image;
		daoArticleAttachments.createArticleAttachment(1, value, description, urlResourceType);
		List<UrlResource> attachments = daoArticleAttachments.getArticleAttachments(1);
		assertNotNull(attachments);
		assertEquals(1, attachments.size());
		UrlResource urlResource = attachments.get(0);
		assertEquals(urlResourceType, urlResource.getUrlResourceType());
	}

	@Test
	public void testGetArticleAttachmentsByEntity() {
		byte[] value = new byte[1];
		String description = "description";
		UrlResourceType urlResourceType = UrlResourceType.Image;
		daoArticleAttachments.createArticleAttachment(1, value, description, urlResourceType);
		Article article = new Article();
		article.setId(1);
		daoArticleAttachments.getArticleAttachments(article);
		List<UrlResource> attachments = article.getAttachments();
		assertNotNull(attachments);
		assertEquals(1, attachments.size());
		UrlResource urlResource = attachments.get(0);
		assertEquals(urlResourceType, urlResource.getUrlResourceType());
	}

	@Test
	public void testGetArticleAttachmentsByEntities() {
		byte[] value = new byte[1];
		String description = "description";
		UrlResourceType urlResourceType = UrlResourceType.Image;
		daoArticleAttachments.createArticleAttachment(1, value, description, urlResourceType);
		Article article = new Article();
		article.setId(1);
		Set<Article> articles = new HashSet<Article>();
		articles.add(article);
		daoArticleAttachments.getArticleAttachments(articles);
		List<UrlResource> attachments = article.getAttachments();
		assertNotNull(attachments);
		assertEquals(1, attachments.size());
		UrlResource urlResource = attachments.get(0);
		assertEquals(urlResourceType, urlResource.getUrlResourceType());
	}
}
