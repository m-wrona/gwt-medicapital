package com.medicapital.client.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.login.LoginCommand;
import com.medicapital.common.commands.login.LoginCommandResp;
import com.medicapital.common.commands.login.LogoutCommand;
import com.medicapital.common.commands.login.LogoutCommandResp;
import com.medicapital.common.entities.LoginData;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SessionManager.class })
public class LoginPresenterTest {

	@Test
	public void testLogin() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);
		final LoginData loginData = new LoginData();
		loginData.setLogin("testUser");
		Date sessionExpiryDate = new Date();
		String sessionId = "sessionId";
		final LoginCommandResp loginResp = new LoginCommandResp(sessionId, loginData, sessionExpiryDate);
		serviceAccess.addResponse(loginResp);
		when(loginView.getLogin()).thenReturn("testUser");
		when(loginView.getPassword()).thenReturn("testPassword");
		loginPresenter.login();
		assertEquals(1, serviceAccess.getReceived().size());
		final LoginCommand loginCommand = (LoginCommand) serviceAccess.getReceived().get(0);
		assertEquals("testUser", loginCommand.getLogin());
		assertEquals("testPassword", new String(loginCommand.getPassword()));
		verify(loggedUserView).setLogin("testUser");
		verify(loggedUserView, times(2)).setLastLoginDate(null);
		verify(sessionManager).createSession(sessionId, loginData, sessionExpiryDate, true);
		loginPresenter.clearPresenter();
	}

	@Test
	public void testWrongLoginData() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);
		final LoginCommandResp loginResp = new LoginCommandResp(null, null, null);
		serviceAccess.addResponse(loginResp);
		when(loginView.getLogin()).thenReturn("testUser");
		when(loginView.getPassword()).thenReturn("wrongPassword");
		loginPresenter.login();
		assertEquals(1, serviceAccess.getReceived().size());
		final LoginCommand loginCommand = (LoginCommand) serviceAccess.getReceived().get(0);
		assertEquals("testUser", loginCommand.getLogin());
		assertEquals("wrongPassword", new String(loginCommand.getPassword()));
		verify(loginView).setWrongLoginDataMessageVisible(true);
	}

	@Test
	public void testLogout() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);

		// mock login
		final LoginData loginData = new LoginData();
		loginData.setLogin("testUser");
		when(sessionManager.getLoginData()).thenReturn(loginData);

		// logout
		serviceAccess.addResponse(new LogoutCommandResp(true));
		loginPresenter.logout();
		assertEquals(1, serviceAccess.getReceived().size());
		assertEquals(LogoutCommand.class, serviceAccess.getReceived().get(0).getClass());
		verify(sessionManager).closeSession();
	}

	@Test
	public void testHandleLoginEvent() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);
		assertNotNull(loginPresenter); // dummy check
		final LoginData loginData = new LoginData();
		loginData.setLogin("testUser");
		eventBus.fireEvent(new LoginEvent(loginData, loginData));
	}

	@Test
	public void testHandleLogoutEvent() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);
		assertNotNull(loginPresenter); // dummy check

		// mock login
		final LoginData loginData = new LoginData();
		loginData.setLogin("testUser");
		when(sessionManager.getLoginData()).thenReturn(loginData);

		// logout
		serviceAccess.addResponse(new LogoutCommandResp(false));
		loginPresenter.logout();
		assertEquals(1, serviceAccess.getReceived().size());
		assertEquals(LogoutCommand.class, serviceAccess.getReceived().get(0).getClass());
		verify(sessionManager, times(0)).closeSession();

		eventBus.fireEvent(new LoginEvent(loginView));
		verify(loggedUserView, times(2)).setLogoutButtonEnabled(true);
	}

	@Test
	public void testRegisterNewUser() {
		final LoginView loginView = mock(LoginView.class);
		final LoggedUserView loggedUserView = mock(LoggedUserView.class);
		final TestServiceAccess serviceAccess = new TestServiceAccess();
		final EventBus eventBus = spy(new SimpleEventBus());
		final SessionManager sessionManager = mock(SessionManager.class);
		final LoginPresenter loginPresenter = new LoginPresenter(loginView, loggedUserView, eventBus, serviceAccess, sessionManager);
		loginPresenter.registerNewUser();
		verify(eventBus).fireEvent(any(GwtEvent.class));
	}
}
