package com.medicapital.server.security;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.entities.UserRole;
import com.medicapital.server.database.DaoLogin;
import com.medicapital.server.util.EmailService;
import com.medicapital.server.util.PasswordGenerator;

public class SecurityManagerTest {

	private SecurityManager securityManager;
	private DaoLogin daoLogin;
	private EmailService emailService;
	private PasswordGenerator passwordGenerator;

	@Before
	public void init() {
		emailService = mock(EmailService.class);
		passwordGenerator = mock(PasswordGenerator.class);
		daoLogin = mock(DaoLogin.class);
		securityManager = new SecurityManager();
		securityManager.setDaoLogin(daoLogin);
		securityManager.setEmailService(emailService);
		securityManager.setPasswordGenerator(passwordGenerator);
	}

	@Test
	public void testLogin() {
		String password = "pass";
		HttpSession httpSession = mock(HttpSession.class);
		LoginData loginData = new LoginData();
		when(daoLogin.loggin(anyString(), any(char[].class))).thenReturn(loginData);
		SessionData sessionData = securityManager.loggin("user", password.toCharArray(), httpSession);
		assertNotNull(sessionData);
		assertNotNull(sessionData.getLoginData());
		verify(daoLogin).setLastLoginDate(anyString(), any(Date.class));
	}

	@Test
	public void testLogout() {
		HttpSession httpSession = mock(HttpSession.class);
		LoginData loginData = new LoginData();
		loginData.setLogin("user");
		SessionData sessionData = spy(new SessionData(httpSession, loginData));
		securityManager.setSessionData(sessionData);
		securityManager.logout("user");
		verify(sessionData).invalidate();
	}

	@Test
	public void testSendForgottenPassword() {
		when(passwordGenerator.generate()).thenReturn("newPass");
		when(daoLogin.changePassword(anyString(), anyString(), any(char[].class))).thenReturn(true);
		securityManager.sendForgottenPassword("login", "address@medicapital.com");
		verify(passwordGenerator).generate();
		verify(daoLogin).changePassword(anyString(), anyString(), any(char[].class));
		verify(emailService).send(anyString(), anyString(), anyString());
	}

	@Test
	public void testAccessRight() throws Throwable {
		HttpSession httpSession = mock(HttpSession.class);
		LoginData loginData = new LoginData();
		loginData.setRole(UserRole.User);
		loginData.setLogin("user");
		SessionData sessionData = spy(new SessionData(httpSession, loginData));
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		Secured secured = mock(Secured.class);
		when(secured.role()).thenReturn(UserRole.User);
		securityManager.setSessionData(sessionData);
		securityManager.checkAccessRights(joinPoint, secured);
		verify(joinPoint).proceed();
	}

	@Test(expected = SecurityException.class)
	public void testAccessRightError() throws Throwable {
		HttpSession httpSession = mock(HttpSession.class);
		LoginData loginData = new LoginData();
		loginData.setRole(UserRole.User);
		loginData.setLogin("user");
		SessionData sessionData = spy(new SessionData(httpSession, loginData));
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
		Secured secured = mock(Secured.class);
		when(secured.role()).thenReturn(UserRole.Doctor);
		securityManager.setSessionData(sessionData);
		securityManager.checkAccessRights(joinPoint, secured);
	}
}
