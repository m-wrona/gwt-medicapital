package com.medicapital.client.article;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;

public class ArticleListPresenterTest {

	@Test
	public void testDisplayArticles() {
		TestEventBus eventBus = new TestEventBus();
		ArticleListView view = mock(ArticleListView.class);
		ArticleUrlService articleUrlService = mock(ArticleUrlService.class);
		ArticleListPresenter articleListPresenter = new ArticleListPresenter(view, eventBus);
		articleListPresenter.setArticleUrlService(articleUrlService);
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
		verify(articleUrlService).getArticleMainPhoto(article.getId());
		verify(view).addArticle(article.getId(), article.getTitle(), article.getBody(), article.getAuthorLastName() + " " + article.getAuthorFirstName(), article.getCreateDate(), article.getLastUpdate(), null);

	}
}
