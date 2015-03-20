package com.medicapital.server.util;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.medicapital.common.dao.ServerException;

public class EmailService {

	private Session session;
	private Transport transport;
	private String serverUser;
	private String serverPassword;
	private String transportProtocol;
	private String smtpHost;
	private int smtpPort;
	private boolean authentication;
	private boolean debug;
	private String socketFactoryClass;
	private boolean socketFactoryFallback;

	public void init() throws ServerException {
		try {
			final Properties props = new Properties();
			props.put("mail.transport.protocol", transportProtocol);
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.host", smtpHost);
			props.put("mail.auth", authentication);
			props.put("mail.smtp.auth", authentication);
			props.put("mail.smtp.port", smtpPort);
			props.put("mail.debug", debug);
			props.put("mail.smtp.socketFactory.port", smtpPort);
			props.put("mail.smtp.socketFactory.class", socketFactoryClass);
			props.put("mail.smtp.socketFactory.fallback", socketFactoryFallback);

			session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(serverUser, serverPassword);
				}
			});
			session.setDebug(true);
			transport = session.getTransport();
			transport.connect(serverUser, serverPassword);
		} catch (final MessagingException e) {
			throw new ServerException("Init e-Mail service error", e);
		}
	}

	public void send(final String to, final String subject, final String body) throws ServerException {
		try {
			tracer(this).debug("Sending e-mail to: " + to + ", subject: " + subject);
			final InternetAddress addressFrom = new InternetAddress(serverUser);
			final MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(subject);
			message.setContent(body, "text/plain");
			final InternetAddress[] addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(to);
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setSentDate(new Date());
			transport.sendMessage(message, message.getAllRecipients());
			tracer(this).debug("E-mail to: " + to + " sent successfully");
		} catch (final MessagingException e) {
			tracer(this).error("E-mail to: " + to + " not sent - error: " + e.getMessage(), e);
			throw new ServerException("E-Mail service error", e);
		}
	}

	public void close() throws ServerException {
		try {
			if (transport != null) {
				transport.close();
				transport = null;
			}
		} catch (final MessagingException e) {
			throw new ServerException("Error occured while closing e-mail service", e);
		}
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getTransportProtocol() {
		return transportProtocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public boolean isAuthentication() {
		return authentication;
	}

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}

	public void setSocketFactoryClass(String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}

	public boolean isSocketFactoryFallback() {
		return socketFactoryFallback;
	}

	public void setSocketFactoryFallback(boolean socketFactoryFallback) {
		this.socketFactoryFallback = socketFactoryFallback;
	}

}
