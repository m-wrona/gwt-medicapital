package com.medicapital.server.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailServiceTest {

	private EmailService emailService;

	@Before
	public void init() {
		emailService = new EmailService();
		emailService.setServerUser("medicapital@poczta.fm");
		emailService.setServerPassword("#zL7@m!k");
		emailService.setTransportProtocol("smtps");
		emailService.setSmtpHost("www.poczta.fm");
		emailService.setSmtpPort(587);
		emailService.setAuthentication(true);
		emailService.setDebug(true);
		emailService.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
		emailService.setSocketFactoryFallback(false);
		emailService.init();
	}

	@After
	public void clean() {
		emailService.close();
		emailService = null;
	}

	@Test
	public void testSendEmail() {
		emailService.send("michal.robert.wronski@gmail.com", "E-mail service test", "test");
	}
}
