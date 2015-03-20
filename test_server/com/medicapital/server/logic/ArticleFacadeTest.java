package com.medicapital.server.logic;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.server.database.DaoArticle;
import com.medicapital.server.database.DaoArticleAttachments;

public class ArticleFacadeTest {

	private ArticleFacade articleFacade;
	private DaoArticle daoArticle;
	private DaoArticleAttachments daoArticleAttachments;

	@Before
	public void init() {
		articleFacade = new ArticleFacade();
		daoArticle = mock(DaoArticle.class);
		daoArticleAttachments = mock(DaoArticleAttachments.class);
		articleFacade.setDaoArticle(daoArticle);
		articleFacade.setDaoArticleAttachments(daoArticleAttachments);
	}

	@Test
	public void testCreateArticle() {
		Article article = new Article();
		articleFacade.create(article);
		assertNotNull(article.getCreateDate());
		verify(daoArticle).create(article);
	}

	@Test
	public void testEditArticle() {
		Article article = new Article();
		articleFacade.update(article);
		assertNotNull(article.getLastUpdate());
		verify(daoArticle).update(article);
	}

	@Test
	public void testDeleteArticle() {
		articleFacade.delete(1);
		verify(daoArticle).delete(1);
	}

	@Test
	public void testFindArticlesCount() {
		String title = "title";
		Date dateFrom = new Date();
		Date dateTo = new Date();
		String authorFirstName = "authorFirstName";
		String authorLastName = "authorLastName";
		articleFacade.findArticlesCount(title, dateFrom, dateTo, authorFirstName, authorLastName);
		verify(daoArticle).findArticlesCount(title, dateFrom, dateTo, authorFirstName, authorLastName);
	}

	@Test
	public void testFindArticles() {
		String title = "title";
		Date dateFrom = new Date();
		Date dateTo = new Date();
		String authorFirstName = "authorFirstName";
		String authorLastName = "authorLastName";
		articleFacade.findArticles(title, dateFrom, dateTo, authorFirstName, authorLastName, 0, 10);
		verify(daoArticle).findArticles(title, dateFrom, dateTo, authorFirstName, authorLastName, 0, 10);
	}

	@Test
	public void testDeleteArticleAttachment() {
		articleFacade.deleteArticleAttachment(1, 2);
		verify(daoArticleAttachments).deleteArticleAttachment(1, 2);
	}

	@Test
	public void testCreateArticleAttachment() {
		int articleId = 1;
		byte[] value = new byte[1];
		String description = "description";
		articleFacade.createArticleAttachment(articleId, value, description, UrlResourceType.Image);
		verify(daoArticleAttachments).createArticleAttachment(articleId, value, description, UrlResourceType.Image);
	}

	@Test
	public void testGetArticleAttachments() {
		articleFacade.getArticleAttachment(1, 2);
		verify(daoArticleAttachments).getArticleAttachment(1, 2);
	}

	@Test
	public void testGetArticleMainPhoto() {
		articleFacade.getArticleMainPhoto(1);
		verify(daoArticle).getArticleMainPhoto(1);
	}

	@Test
	public void testDeleteArticleMainPhoto() {
		articleFacade.deleteArticleMainPhoto(1);
		verify(daoArticle).updateArticleMainPhoto(1, null);
	}

	@Test
	public void testSaveArticleMainPhoto() {
		byte[] value = new byte[1];
		articleFacade.saveArticleMainPhoto(1, value);
		verify(daoArticle).updateArticleMainPhoto(1, value);
	}
	
	@Test
	public void testGetDoctorArticle() {
		articleFacade.getDoctorArticle(1);
		verify(daoArticle).get(1, null);
	}
	
	@Test
	public void testGetDoctorArticles() {
		articleFacade.getDoctorArticles("doctor", 0, 10);
		verify(daoArticle).getArticles("doctor", 0, 10, null);
	}
	
	@Test
	public void testGetDoctorArticlesCount() {
		articleFacade.getDoctorArticlesCount("doctor");
		verify(daoArticle).getArticlesCount("doctor", null);
	}
	
	@Test
	public void testGetPublishedArticles() {
		articleFacade.getPublishedArticles("user", 0, 10);
		verify(daoArticle).getArticles("user", 0, 10, true);
	}
	
	@Test
	public void testGetPublishedArticlesCount() {
		articleFacade.getPublishedArticlesCount("user");
		verify(daoArticle).getArticlesCount("user", true);
	}
}
