package com.medicapital.server.database;

import static org.mockito.Mockito.*;
import java.util.Date;
import org.junit.Test;

import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.entities.Article;
import com.medicapital.server.database.hibernate.HibernateArticle;
import com.medicapital.server.database.hibernate.entities.ArticleAttachmentsEntity;
import com.medicapital.server.database.hibernate.entities.ArticleEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoArticleTest extends AbstractDBTestCase {

	private HibernateArticle daoArticle;
	private DaoArticleAttachments daoArticleAttachments;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(ArticleEntity.class);
		addAnnotatedClass(ArticleAttachmentsEntity.class);
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		daoArticle = new HibernateArticle();
		daoArticle.setSessionFactory(getHibernateSessionFactory());
		daoArticleAttachments = mock(DaoArticleAttachments.class);
		daoArticle.setDaoArticleAttachments(daoArticleAttachments);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoArticle.close();
	}

	@Test
	public void testGetArticle() {
		Article article = daoArticle.get(1);
		assertNotNull(article);
		assertEquals(1, article.getId());
		assertEquals("testUser", article.getAuthorLogin());
		verify(daoArticleAttachments).getArticleAttachments(any(Article.class));
	}

	@Test
	public void testFindArticlesCount() {
		assertEquals(3, daoArticle.findArticlesCount("Title", null, null, null, null));
		assertEquals(1, daoArticle.findArticlesCount("Title 1", null, null, null, null));
		assertEquals(2, daoArticle.findArticlesCount(null, null, null, "firstName", null));
		DateManager dateManager = DateFactory.createDateManager();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		dateManager.setDate(dateFrom, 2010, 1, 1);
		dateManager.setDate(dateTo, 2010, 1, 10);
		assertEquals(3, daoArticle.findArticlesCount(null, dateFrom, dateTo, null, null));
		dateManager.setDate(dateFrom, 2010, 1, 1);
		dateManager.setDate(dateTo, 2010, 1, 5);
		assertEquals(3, daoArticle.findArticlesCount(null, dateFrom, dateTo, null, null));
		dateManager.setDate(dateFrom, 2010, 2, 1);
		dateManager.setDate(dateTo, 2010, 2, 10);
		assertEquals(0, daoArticle.findArticlesCount(null, dateFrom, dateTo, null, null));
	}

	@Test
	public void testFindArticles() {
		assertEquals(3, daoArticle.findArticles("Title", null, null, null, null, 0, 10).size());
		assertEquals(1, daoArticle.findArticles("Title 1", null, null, null, null, 0, 10).size());
		assertEquals(2, daoArticle.findArticles(null, null, null, "firstName", null, 0, 10).size());
		DateManager dateManager = DateFactory.createDateManager();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		dateManager.setDate(dateFrom, 2010, 1, 1);
		dateManager.setDate(dateTo, 2010, 1, 10);
		assertEquals(3, daoArticle.findArticles(null, dateFrom, dateTo, null, null, 0, 10).size());
		dateManager.setDate(dateFrom, 2010, 1, 1);
		dateManager.setDate(dateTo, 2010, 1, 5);
		assertEquals(3, daoArticle.findArticles(null, dateFrom, dateTo, null, null, 0, 10).size());
		dateManager.setDate(dateFrom, 2010, 2, 1);
		dateManager.setDate(dateTo, 2010, 2, 10);
		assertNull(daoArticle.findArticles(null, dateFrom, dateTo, null, null, 0, 10));
	}

	@Test
	public void testArticleMainPhoto() {
		assertNull(daoArticle.getArticleMainPhoto(1));
		byte[] photo = new byte[1];
		daoArticle.updateArticleMainPhoto(1, photo);
		byte[] updatedPhoto = daoArticle.getArticleMainPhoto(1);
		assertNotNull(updatedPhoto);
		assertEquals(1, updatedPhoto.length);
	}

	@Test
	public void testGetPublishedArticle() {
		Article article = daoArticle.get(1, true);
		assertNotNull(article);
		assertEquals(1, article.getId());
		assertEquals("testUser", article.getAuthorLogin());
		verify(daoArticleAttachments).getArticleAttachments(any(Article.class));
		assertNull(daoArticle.get(4, true));
	}

	@Test
	public void testGetNotPublishedArticle() {
		Article article = daoArticle.get(4, false);
		assertNotNull(article);
		assertEquals(4, article.getId());
		assertEquals("notPublishedUser", article.getAuthorLogin());
		verify(daoArticleAttachments).getArticleAttachments(any(Article.class));
		assertNull(daoArticle.get(1, false));
	}

	@Test
	public void testGetArticlesById() {
		assertEquals(2, daoArticle.getArticlesCount(1, true));
		assertEquals(2, daoArticle.getArticles(1, 0, 10, true).size());
		assertEquals(2, daoArticle.getArticlesCount(1, null));
		assertEquals(2, daoArticle.getArticles(1, 0, 10, null).size());
		assertEquals(0, daoArticle.getArticlesCount(1, false));
		assertNull(daoArticle.getArticles(1, 0, 10, false));
	}

	@Test
	public void testGetArticlesByLogin() {
		assertEquals(2, daoArticle.getArticlesCount("testUser", true));
		assertEquals(2, daoArticle.getArticles("testUser", 0, 10, true).size());
		assertEquals(2, daoArticle.getArticlesCount("testUser", null));
		assertEquals(2, daoArticle.getArticles("testUser", 0, 10, null).size());
		assertEquals(0, daoArticle.getArticlesCount("testUser", false));
		assertNull(daoArticle.getArticles("testUser", 0, 10, false));
	}

}
