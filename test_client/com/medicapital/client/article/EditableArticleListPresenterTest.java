package com.medicapital.client.article;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.common.entities.Article;

public class EditableArticleListPresenterTest {

	@Test
	public void testDisplayArticles() {
		TestEventBus eventBus = new TestEventBus();
		EditableArticleListView view = mock(EditableArticleListView.class);
		EditableArticleListPresenter articleListPresenter = new EditableArticleListPresenter(view, eventBus);
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
		article.setId(1);
		article.setTitle("Title");
		article.setBody("Body");
		article.setCreateDate(new Date());
		article.setAuthorFirstName("First name");
		article.setAuthorLastName("Last name");
		articles.add(article);
		articleListPresenter.display(articles);
		verify(view).addArticle(article.getId(), article.getTitle(), article.getBody(), article.getCreateDate(), article.getLastUpdate(), article.isPublished());
	}

	@Test
	public void testEditArticle() {
		TestEventBus eventBus = new TestEventBus();
		EditableArticleListView view = mock(EditableArticleListView.class);
		EditableArticleListPresenter articleListPresenter = new EditableArticleListPresenter(view, eventBus);
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
		article.setId(1);
		article.setTitle("Title");
		article.setBody("Body");
		article.setCreateDate(new Date());
		article.setAuthorFirstName("First name");
		article.setAuthorLastName("Last name");
		articles.add(article);
		articleListPresenter.display(articles);
		verify(view).addArticle(article.getId(), article.getTitle(), article.getBody(), article.getCreateDate(), article.getLastUpdate(), article.isPublished());
		assertTrue(eventBus.getReceivedEvents().isEmpty());
		articleListPresenter.editArticle(1);
		assertEquals(1, eventBus.getReceivedEvents().size());
	}
}
