package com.medicapital.client.login;

import static com.medicapital.client.log.Tracer.tracer;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.login.ForgotPasswordCommand;
import com.medicapital.common.commands.login.ForgotPasswordCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.validation.ValidationUtils;

/**
 * Presenter for sending forgotten password
 * 
 * @author mwronski
 * 
 */
final public class ForgotPasswordPresenter {

	private final ForgotPasswordView display;
	private final ServiceAccess loginService;

	ForgotPasswordPresenter(final ForgotPasswordView display, final ServiceAccess userService) {
		this.display = display;
		loginService = userService;
	}

	/**
	 * Send e-mail about forgotten password
	 * 
	 */
	final public void sendForgotPasswordEmail() {
		final String login = display.getLogin();
		final String eMail = display.getEmail();
		tracer(this).debug("Sending forgotten password - e-mail: " + eMail + ", login: " + login);
		display.setServerErrorMsgVisible(false);
		display.setEMailSentMsgVisible(false);
		display.setLoginEmailNotFoundMsgVisible(false);
		display.setLoginOrEmailNotValidMsgVisible(false);
		if (ValidationUtils.isEmpty(login) || !ValidationUtils.isEmailValid(eMail)) {
			// validation error
			tracer(this).debug("E-Mail or login is not valid - canceling operation");
			display.setLoginOrEmailNotValidMsgVisible(true);
			return;
		}
		display.setSendPasswordHandlerEnabled(false);

		loginService.execute(new ForgotPasswordCommand(login, eMail), new ResponseHandler<LoginData>() {

			@Override
			public void handle(final CommandResp<LoginData> response) {
				if (response instanceof ForgotPasswordCommandResp) {
					display.setSendPasswordHandlerEnabled(true);
					final ForgotPasswordCommandResp passResp = (ForgotPasswordCommandResp) response;
					tracer(this).debug("Password sent: " + passResp.isEmailSent());
					if (passResp.isEmailSent()) {
						display.setEMailSentMsgVisible(true);
					} else {
						display.setLoginEmailNotFoundMsgVisible(true);
					}
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				display.setServerErrorMsgVisible(true);
			}
		});
	}

}
