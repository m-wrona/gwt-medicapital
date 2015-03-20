package com.medicapital.client.article;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Test;

import com.medicapital.client.core.commands.EntityCommandFactory;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.User;

public class CreateArticlePresenterTest {

	@Test
	public void testInitCreateArticlePresenter() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateArticleView view = mock(CreateArticleView.class);
		EditArticleImagePresenter articlePhoto = mock(EditArticleImagePresenter.class);
		CreateArticlePresenter createArticlePresenter = spy(new CreateArticlePresenter(view, eventBus));
		createArticlePresenter.setEditImagePresenter(articlePhoto);
		createArticlePresenter.setServiceAccess(serviceAccess);
		createArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setCreateCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setEditAttachmentsPresenter(mock(EditArticleAttachmentsPresenter.class));
		SelectCommandResp<User> response = new SelectCommandResp<User>(User.class);
		User user = new User();
		response.add(user);
		user.setLogin("testUser");
		serviceAccess.addResponse(response);
		createArticlePresenter.init(1);
		assertEquals(2, serviceAccess.getReceived().size());
		assertEquals(SelectCommand.class, serviceAccess.getReceived().get(0).getClass());
		assertEquals(CreateCommand.class, serviceAccess.getReceived().get(1).getClass());
		verify(createArticlePresenter).display(any(Article.class));
		verify(createArticlePresenter).saveChanges();
		verify(view).setViewBlocked(true);
		verify(view).setViewBlocked(false);
		@SuppressWarnings("unchecked")
		CreateCommand<Article> createCommand = (CreateCommand<Article>) serviceAccess.getReceived().get(1);
		assertFalse(createCommand.getElementToCreate().isPublished());
		assertFalse(createArticlePresenter.isElementCreatedByUser());
	}

	@Test
	public void testAutoSave() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateArticleView view = mock(CreateArticleView.class);
		EditArticleImagePresenter articlePhoto = mock(EditArticleImagePresenter.class);
		CreateArticlePresenter createArticlePresenter = spy(new CreateArticlePresenter(view, eventBus));
		createArticlePresenter.setEditImagePresenter(articlePhoto);
		createArticlePresenter.setServiceAccess(serviceAccess);
		createArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setCreateCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setEditAttachmentsPresenter(mock(EditArticleAttachmentsPresenter.class));
		SelectCommandResp<User> response = new SelectCommandResp<User>(User.class);
		User user = new User();
		response.add(user);
		user.setLogin("testUser");
		serviceAccess.addResponse(response);
		serviceAccess.addResponse(new CreateCommandResp<Article>(Article.class, 1));
		createArticlePresenter.init(1);
		assertEquals(2, serviceAccess.getReceived().size());
		assertEquals(SelectCommand.class, serviceAccess.getReceived().get(0).getClass());
		assertEquals(CreateCommand.class, serviceAccess.getReceived().get(1).getClass());
		createArticlePresenter.saveChanges();
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(UpdateCommand.class, serviceAccess.getReceived().get(2).getClass());
		@SuppressWarnings("unchecked")
		UpdateCommand<Article> updateCommand = (UpdateCommand<Article>) serviceAccess.getReceived().get(2);
		assertFalse(updateCommand.getElementToEdit().isPublished());
	}

	@Test
	public void testCreateArticle() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateArticleView view = mock(CreateArticleView.class);
		EditArticleImagePresenter articlePhoto = mock(EditArticleImagePresenter.class);
		CreateArticlePresenter createArticlePresenter = spy(new CreateArticlePresenter(view, eventBus));
		createArticlePresenter.setEditImagePresenter(articlePhoto);
		createArticlePresenter.setServiceAccess(serviceAccess);
		createArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setCreateCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setEditAttachmentsPresenter(mock(EditArticleAttachmentsPresenter.class));
		SelectCommandResp<User> response = new SelectCommandResp<User>(User.class);
		User user = new User();
		response.add(user);
		user.setLogin("testUser");
		serviceAccess.addResponse(response);
		serviceAccess.addResponse(new CreateCommandResp<Article>(Article.class, 1));
		UpdateCommandResp<Article> updateCommandResp = new UpdateCommandResp<Article>(Article.class, 1);
		serviceAccess.addResponse(updateCommandResp);
		createArticlePresenter.init(1);
		createArticlePresenter.create();
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(SelectCommand.class, serviceAccess.getReceived().get(0).getClass());
		assertEquals(CreateCommand.class, serviceAccess.getReceived().get(1).getClass());
		verify(view, times(6)).setLastUpdateDate(any(Date.class));
		@SuppressWarnings("unchecked")
		UpdateCommand<Article> updateCommand = (UpdateCommand<Article>) serviceAccess.getReceived().get(2);
		assertFalse(updateCommand.getElementToEdit().isPublished());
		assertTrue(createArticlePresenter.isElementCreatedByUser());
	}

	@Test
	public void testCancelCreatedArticleByUser() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateArticleView view = mock(CreateArticleView.class);
		EditArticleImagePresenter articlePhoto = mock(EditArticleImagePresenter.class);
		CreateArticlePresenter createArticlePresenter = spy(new CreateArticlePresenter(view, eventBus));
		createArticlePresenter.setEditImagePresenter(articlePhoto);
		createArticlePresenter.setServiceAccess(serviceAccess);
		createArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setCreateCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setEditAttachmentsPresenter(mock(EditArticleAttachmentsPresenter.class));
		SelectCommandResp<User> response = new SelectCommandResp<User>(User.class);
		User user = new User();
		response.add(user);
		user.setLogin("testUser");
		serviceAccess.addResponse(response);
		serviceAccess.addResponse(new CreateCommandResp<Article>(Article.class, 1));
		UpdateCommandResp<Article> updateCommandResp = new UpdateCommandResp<Article>(Article.class, 1);
		serviceAccess.addResponse(updateCommandResp);
		createArticlePresenter.init(1);
		createArticlePresenter.create();
		assertEquals(true, createArticlePresenter.isElementCreatedByUser());
		serviceAccess.addResponse(new DeleteCommandResp<Article>(Article.class, 1));
		createArticlePresenter.cancel();
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(SelectCommand.class, serviceAccess.getReceived().get(0).getClass());
		assertEquals(CreateCommand.class, serviceAccess.getReceived().get(1).getClass());
		assertEquals(UpdateCommand.class, serviceAccess.getReceived().get(2).getClass());
	}

	@Test
	public void testCancelArticleCreation() {
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateArticleView view = mock(CreateArticleView.class);
		EditArticleImagePresenter articlePhoto = mock(EditArticleImagePresenter.class);
		CreateArticlePresenter createArticlePresenter = spy(new CreateArticlePresenter(view, eventBus));
		createArticlePresenter.setEditImagePresenter(articlePhoto);
		createArticlePresenter.setServiceAccess(serviceAccess);
		createArticlePresenter.setDisplayCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setCreateCommandFactory(new EntityCommandFactory<Article>(Article.class));
		createArticlePresenter.setEditAttachmentsPresenter(mock(EditArticleAttachmentsPresenter.class));
		SelectCommandResp<User> response = new SelectCommandResp<User>(User.class);
		User user = new User();
		response.add(user);
		user.setLogin("testUser");
		serviceAccess.addResponse(response);
		serviceAccess.addResponse(new CreateCommandResp<Article>(Article.class, 1));
		createArticlePresenter.init(1);
		serviceAccess.addResponse(new DeleteCommandResp<Article>(Article.class, 1));
		createArticlePresenter.cancel();
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(SelectCommand.class, serviceAccess.getReceived().get(0).getClass());
		assertEquals(CreateCommand.class, serviceAccess.getReceived().get(1).getClass());
		assertEquals(DeleteCommand.class, serviceAccess.getReceived().get(2).getClass());
	}
}
