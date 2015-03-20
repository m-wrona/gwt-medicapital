package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.login.ForgotPasswordCommand;
import com.medicapital.common.commands.login.ForgotPasswordCommandResp;
import com.medicapital.common.commands.login.LoginCommand;
import com.medicapital.common.commands.login.LoginCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.LoginAccess;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionData;
import com.medicapital.server.security.SessionFactory;

public class LoginAccessTest {

	private SecurityManager securityFacade;
	private LoginAccess loginAccess;

	@Before
	public void init() {
		securityFacade = mock(SecurityManager.class);
		loginAccess = new LoginAccess();
		loginAccess.setSecurityManager(securityFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		loginAccess.setSessionFactory(sessionFactory);
	}

	@Test
	public void testLogin() {
		LoginData loginData = new LoginData();
		loginData.setLogin("wrona");
		SessionData userInfo = mock(SessionData.class);
		when(userInfo.getLoginData()).thenReturn(loginData);
		final LoginCommand loginCommand = new LoginCommand("wrona", "wrona");
		when(securityFacade.loggin(anyString(), any(char[].class), any(HttpSession.class))).thenReturn(userInfo);
		final ServerResponse<LoginData> serverResponse = loginAccess.execute(new ServerRequest<LoginData>(loginCommand));
		verify(securityFacade).loggin(anyString(), any(char[].class), any(HttpSession.class));
		assertNotNull(serverResponse);
		assertEquals(LoginCommandResp.class, serverResponse.getResponse().getClass());
		final LoginCommandResp loginResp = (LoginCommandResp) serverResponse.getResponse();
		assertNotNull(loginResp.getLoginData());
		assertEquals("wrona", loginResp.getLoginData().getLogin());
	}

	@Test
	public void testWrongLoginData() {
		final LoginCommand loginCommand = new LoginCommand("wrona", "wrongPass");
		final ServerResponse<LoginData> serverResponse = loginAccess.execute(new ServerRequest<LoginData>(loginCommand));
		assertNotNull(serverResponse);
		assertEquals(LoginCommandResp.class, serverResponse.getResponse().getClass());
		final LoginCommandResp loginResp = (LoginCommandResp) serverResponse.getResponse();
		assertNull(loginResp.getLoginData());
	}

	@Test
	public void testHandleForgotPasswordCommand() {
		when(securityFacade.sendForgottenPassword("login", "jan.kowalski@medicapital.pl")).thenReturn(true);
		final ForgotPasswordCommand forgotPasswordCommand = new ForgotPasswordCommand("login", "jan.kowalski@medicapital.pl");
		final ServerResponse<LoginData> serverResponse = loginAccess.execute(new ServerRequest<LoginData>(forgotPasswordCommand));
		verify(securityFacade).sendForgottenPassword("login", "jan.kowalski@medicapital.pl");
		assertNotNull(serverResponse);
		final ForgotPasswordCommandResp commandResp = (ForgotPasswordCommandResp) serverResponse.getResponse();
		assertTrue(commandResp.isEmailSent());
	}

	@Test
	public void testWrongCommandType() {
		try {
			loginAccess.execute(new ServerRequest<LoginData>(new SelectCountCommand<LoginData>(LoginData.class)));
			fail("Wrong command type");
		} catch (final ServerException e) {
			// ignore
		}
	}
}
