package com.medicapital.client.article;

import static org.mockito.Mockito.*;
import org.junit.Test;

import com.medicapital.client.core.url.UrlResourceListView;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;

public class ArticlePresenterTest {

	@Test
	public void testDisplayArticle() {
		TestEventBus eventBus = new TestEventBus();
		ArticleView view = mock(ArticleView.class);
		ArticleUrlService articleUrlService = mock(ArticleUrlService.class);
		ArticlePresenter articlePresenter = new ArticlePresenter(view, eventBus);
		articlePresenter.setImageService(articleUrlService);
		UrlResourceListView photoListView = mock(UrlResourceListView.class);
		when(view.getPhotoListView()).thenReturn(photoListView);
		Article article = new Article();
		articlePresenter.display(article);
		verify(articleUrlService).getArticleMainPhoto(article.getId());
	}
}
