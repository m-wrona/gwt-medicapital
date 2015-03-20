package com.medicapital.server.security;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.LoginData;
import com.medicapital.server.database.DaoLogin;
import com.medicapital.server.lang.Lang;
import com.medicapital.server.util.EmailService;
import com.medicapital.server.util.PasswordGenerator;

/**
 * Class manages security and access rights to server resources.
 * 
 * @author mwronski
 * 
 */
@Aspect
public class SecurityManager {

	private DaoLogin daoLogin;
	private EmailService emailService;
	private PasswordGenerator passwordGenerator;
	private SessionData sessionData;

	/**
	 * Check access rights for secured method
	 * 
	 * @param methodInvocation
	 * @param secured
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(secured)")
	public Object checkAccessRights(ProceedingJoinPoint methodInvocation, Secured secured) throws Throwable {
		tracer(this).debug("Checking access rights for invocation: " + methodInvocation);
		tracer(this).debug("Access rights required: " + secured + ", user info: " + sessionData);
		if (sessionData == null || sessionData.getLoginData().getRole().getAccessLevel() < secured.role().getAccessLevel()) {
			// TODO uncomment
			// throw new SecurityException("Access rights required: " + secured
			// + ", got: " + sessionData);
		}
		return methodInvocation.proceed();
	}

	public boolean logout(String login) throws ServerException {
		tracer(this).debug("Logout user: " + login);
		if (sessionData == null) {
			return true;
		} else if (sessionData.getLoginData().getLogin().equals(login)) {
			tracer(this).debug("Logout user: " + login + " - invalidating session");
			sessionData.invalidate();
			return true;
		}
		return false;
	}

	public SessionData loggin(String login, char[] password, HttpSession httpSession) throws ServerException {
		tracer(this).debug("Login user: " + login);
		if (httpSession == null) {
			throw new ServerException("Null HTTP session");
		}
		final LoginData loginData = daoLogin.loggin(login, password);
		if (loginData != null) {
			// user can be logged in
			tracer(this).debug("Updating last login date for user: " + login);
			daoLogin.setLastLoginDate(login, new Date());
			SessionData sessionData = new SessionData(httpSession, loginData);
			tracer(this).debug("User logged in: " + sessionData);
			return sessionData;
		} else {
			tracer(this).debug("Wrong login data: " + login + " - invalidating session...");
			httpSession.invalidate();
			return null;
		}
	}

	public boolean sendForgottenPassword(String login, String eMail) throws ServerException {
		tracer(this).debug("Send user's forgotten password - e-mail: " + eMail + ", login: " + login);
		final String newPassword = passwordGenerator.generate();
		boolean passwordChanged = daoLogin.changePassword(login, eMail, newPassword.toCharArray());
		if (passwordChanged) {
			emailService.send(eMail, Lang.mail().forgottenPasswordTitle(), Lang.mail().forgottenPassword(newPassword));
			tracer(this).debug("E-mail was sent for forgotten password - e-mail: " + eMail + ", login: " + login);
		}
		return passwordChanged;
	}

	public void setDaoLogin(DaoLogin daoLogin) {
		this.daoLogin = daoLogin;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}

}
