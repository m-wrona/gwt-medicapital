package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.User;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.UserCommandExecutor;
import com.medicapital.server.logic.UserFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class UserCommandExecutorTest {

	private static final String testLogin = "commandExecutorTest";
	private UserFacade userFacade;
	private UserCommandExecutor userCommandExecutor;

	@Before
	public void init() {
		userFacade = mock(UserFacade.class);
		userCommandExecutor = new UserCommandExecutor();
		userCommandExecutor.setUserFacade(userFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		userCommandExecutor.setSessionFactory(sessionFactory);
		userCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testGetUserByLogin() {
		final SelectCommand<User> selectCommand = new SelectCommand<User>(User.class, "testLogin");
		User mockUser = new User();
		mockUser.setLogin("testLogin");
		when(userFacade.get("testLogin")).thenReturn(mockUser);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(selectCommand));
		verify(userFacade).get("testLogin");
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
		final SelectCommandResp<User> userResp = (SelectCommandResp<User>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
		final Collection<User> users = userResp.getData();
		assertEquals(1, users.size());
		assertEquals("testLogin", users.iterator().next().getLogin());
	}

	@Test
	public void testGetUserById() {
		final SelectCommand<User> selectCommand = new SelectCommand<User>(User.class, 1);
		User mockUser = new User();
		mockUser.setLogin("testLogin");
		when(userFacade.get(1)).thenReturn(mockUser);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(selectCommand));
		verify(userFacade).get(1);
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
		final SelectCommandResp<User> userResp = (SelectCommandResp<User>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
		final Collection<User> users = userResp.getData();
		assertEquals("testLogin", users.iterator().next().getLogin());
	}

	@Test
	public void testGetUserCount() {
		final SelectCountCommand<User> selectCountCommand = new SelectCountCommand<User>(User.class);
		when(userFacade.getCount()).thenReturn(5);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(selectCountCommand));
		verify(userFacade).getCount();
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCountCommandResp.class, serverResponse.getResponse().getClass());
		final SelectCountCommandResp<User> userResp = (SelectCountCommandResp<User>) serverResponse.getResponse();
		assertEquals(5, userResp.getCount());
	}

	@Test
	public void testGetUserCountByLogin() {
		when(userFacade.isLoginFree("testLogin")).thenReturn(true);
		final SelectCountCommand<User> selectCountCommand = new SelectCountCommand<User>(User.class);
		selectCountCommand.setLogin("testLogin");
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(selectCountCommand));
		verify(userFacade).isLoginFree("testLogin");
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCountCommandResp.class, serverResponse.getResponse().getClass());
		final SelectCountCommandResp<User> userResp = (SelectCountCommandResp<User>) serverResponse.getResponse();
		assertEquals(0, userResp.getCount());
	}

	@Test
	public void testCreateUser() {
		final User user = spy(new User());
		user.setLogin(testLogin);
		user.setEmail("address@gmail.com");
		when(userFacade.create(any(User.class))).thenReturn(6);
		final CreateCommand<User> createCommand = new CreateCommand<User>(User.class, user);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(createCommand));
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(CreateCommandResp.class, serverResponse.getResponse().getClass());
		final CreateCommandResp<User> userResp = (CreateCommandResp<User>) serverResponse.getResponse();
		assertTrue(userResp.wasCreated());
	}

	@Test
	public void testUpdateUser() {
		final User user = new User();
		user.setLogin(testLogin);
		user.setEmail("address@gmail.com");
		user.setPassword("newPass");
		when(userFacade.get(anyString())).thenReturn(new User());
		final UpdateCommand<User> updateCommand = new UpdateCommand<User>(User.class, user);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(updateCommand));
		verify(userFacade).update(user);
		final UpdateCommandResp<User> userResp = (UpdateCommandResp<User>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
	}

	@Test
	public void testDeleteUser() {
		final DeleteCommand<User> deleteCommand = new DeleteCommand<User>(User.class, 1);
		final ServerResponse<User> serverResponse = userCommandExecutor.execute(new ServerRequest<User>(deleteCommand));
		verify(userFacade).delete(1);
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(DeleteCommandResp.class, serverResponse.getResponse().getClass());
		final DeleteCommandResp<User> userResp = (DeleteCommandResp<User>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
	}

}
