package com.medicapital.client.article;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.entities.Article;

public class EditArticlePresenterTest {

	@Test
	public void testSaveEditedArticle() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		EditArticleView view = mock(EditArticleView.class);
		EditArticleImagePresenter editImagePresenter = mock(EditArticleImagePresenter.class);
		EditArticleAttachmentsPresenter editArticleAttachmentsPresenter = mock(EditArticleAttachmentsPresenter.class);
		EditArticlePresenter editArticlePresenter = new EditArticlePresenter(view, eventBus);
		editArticlePresenter.setEditImagePresenter(editImagePresenter);
		editArticlePresenter.setEditAttachmentsPresenter(editArticleAttachmentsPresenter);
		editArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		editArticlePresenter.setEditCommandFactory(new EntityCommandFactory<Article>(Article.class));
		editArticlePresenter.setServiceAccess(serviceAccess);
		Article article = new Article();
		article.setId(1);
		editArticlePresenter.display(article);
		editArticlePresenter.update();
		assertEquals(1, serviceAccess.getReceived().size());
		assertEquals(UpdateCommand.class, serviceAccess.getReceived().get(0).getClass());
	}
}
