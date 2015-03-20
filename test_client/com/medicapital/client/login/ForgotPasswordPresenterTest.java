package com.medicapital.client.login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.login.ForgotPasswordCommand;
import com.medicapital.common.commands.login.ForgotPasswordCommandResp;

public class ForgotPasswordPresenterTest {

	@Test
	public void testSendForgottenPassword() {
		ForgotPasswordView view = mock(ForgotPasswordView.class);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		ForgotPasswordCommandResp commandResp = new ForgotPasswordCommandResp(true);
		serviceAccess.addResponse(commandResp);
		
		ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(view, serviceAccess);
		when(view.getLogin()).thenReturn("login");
		when(view.getEmail()).thenReturn("user@medicapital.pl");
		presenter.sendForgotPasswordEmail();
		assertEquals(1, serviceAccess.getReceived().size());
		ForgotPasswordCommand command = (ForgotPasswordCommand) serviceAccess.getReceived().get(0);
		assertEquals("user@medicapital.pl", command.geteMail());
		verify(view).setEMailSentMsgVisible(true);
	}
	
	@Test
	public void testSendForgottenPasswordNotValidEMail() {
		ForgotPasswordView view = mock(ForgotPasswordView.class);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		ForgotPasswordCommandResp commandResp = new ForgotPasswordCommandResp(false);
		serviceAccess.addResponse(commandResp);
		
		ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(view, serviceAccess);
		when(view.getEmail()).thenReturn("email@wrong");
		presenter.sendForgotPasswordEmail();
		assertEquals(0, serviceAccess.getReceived().size());
		verify(view).setLoginOrEmailNotValidMsgVisible(true);
	}
	
	@Test
	public void testSendForgottenPasswordNotFoundEMail() {
		ForgotPasswordView view = mock(ForgotPasswordView.class);
		TestServiceAccess serviceAccess = new TestServiceAccess();
		ForgotPasswordCommandResp commandResp = new ForgotPasswordCommandResp(false);
		serviceAccess.addResponse(commandResp);
		
		ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(view, serviceAccess);
		when(view.getLogin()).thenReturn("login");
		when(view.getEmail()).thenReturn("user@medicapital.pl");
		presenter.sendForgotPasswordEmail();
		assertEquals(1, serviceAccess.getReceived().size());
		ForgotPasswordCommand command = (ForgotPasswordCommand) serviceAccess.getReceived().get(0);
		assertEquals("user@medicapital.pl", command.geteMail());
		verify(view).setLoginEmailNotFoundMsgVisible(true);
	}
}
