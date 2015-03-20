package com.medicapital.server.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.entities.User;
import com.medicapital.server.database.DaoUser;
import com.medicapital.server.util.EmailService;
import com.medicapital.server.util.PasswordGenerator;

public class UserFacadeTest {

	private DaoUser daoUser;
	private PasswordGenerator passwordGenerator;
	private EmailService emailService;
	private UserFacade userFacade;

	private User createTestUser() {
		User user = new User();
		user.setLogin("testLogin");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEmail("test@email.com");
		return user;
	}

	@Before
	public void init() {
		daoUser = mock(DaoUser.class);
		passwordGenerator = mock(PasswordGenerator.class);
		emailService = mock(EmailService.class);
		userFacade = new UserFacade();
		userFacade.setDaoUser(daoUser);
		userFacade.setEmailService(emailService);
		userFacade.setPasswordGenerator(passwordGenerator);
	}

	@Test
	public void testGetUserByLogin() {
		User mockUser = createTestUser();
		mockUser.setId(1);
		mockUser.setPassword("newPass");
		when(daoUser.get(mockUser.getLogin())).thenReturn(mockUser);
		User user = userFacade.get(mockUser.getLogin());
		assertEquals(user.getLogin(), user.getLogin());
	}

	@Test
	public void testIsLoginFree() {
		User mockUser = createTestUser();
		mockUser.setId(1);
		mockUser.setPassword("newPass");
		when(daoUser.get(mockUser.getLogin())).thenReturn(mockUser);
		assertFalse(userFacade.isLoginFree(mockUser.getLogin()));
	}

	@Test
	public void testGetUsersCount() {
		userFacade.getCount();
		verify(daoUser).getCount();
	}

	@Test
	public void testCreateUser() {
		User user = spy(createTestUser());
		userFacade.create(user);
		verify(daoUser).create(user);
		verify(passwordGenerator).generate();
		verify(user).setPassword(anyString());
		verify(user).setActive(true);
		verify(emailService).send(anyString(), anyString(), anyString());
	}

	@Test
	public void testUpdateUser() {
		User dbUser = createTestUser();
		dbUser.setId(1);
		User user = spy(createTestUser());
		user.setId(1);
		user.setPassword("newPass");
		when(daoUser.get(user.getId())).thenReturn(dbUser);
		userFacade.update(user);
		verify(daoUser).update(user);
		verify(emailService).send(anyString(), anyString(), anyString());
	}
}
