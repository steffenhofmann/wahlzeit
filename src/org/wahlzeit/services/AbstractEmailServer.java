package org.wahlzeit.services;

import java.util.*;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.*;

public abstract class AbstractEmailServer implements EmailServer{
	/**
	 * 
	 */
	protected static final EmailServer REAL_INSTANCE = new DefaultEmailServer();
	protected static final EmailServer MOCK_INSTANCE = new MockEmailServer();
	
	protected static EmailServer instance = getInstanceFromMode();
	
	/**
	 * 
	 */
	protected Session session = null;
	
	/**
	 * @methodtype set
	 */
	public static void setInstance(EmailServer server) {
		instance = server;
	}
	
	/**
	 * @methodtype get
	 */
	public static EmailServer getInstance() {
		return instance;
	}
	
	/**
	 * @methodtype set
	 */
	public static void setMockInstance() {
		instance = MOCK_INSTANCE;
	}
	
	/**
	 * @methodtype get
	 */
	public static EmailServer getInstanceFromMode() {
		if (SysLog.isInProductionMode()) {
			return REAL_INSTANCE;
		} else {
			return MOCK_INSTANCE;
		}
	}
	
	/**
	 * @methodtype init
	 */
	protected AbstractEmailServer() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "localhost");
	    session = Session.getDefaultInstance(properties, null);
	}
	
	abstract void doSendEmail(Message msg) throws Exception;
	
	/**
	 * @methodtype command
	 * @methodproperties convenience
	 */
	public synchronized void sendEmail(EmailAddress from, EmailAddress to, String subject, String body) {
		sendEmail(from, to, EmailAddress.NONE, subject, body);
	}
	
	/**
	 * @methodtype command
	 * @methodproperties composed
	 */
	public synchronized void sendEmail(EmailAddress from, EmailAddress to, EmailAddress bcc, String subject, String body) {
		try {
			Message msg = createMessage(from, to, bcc, subject, body);
			doSendEmail(msg);
		} catch (Exception ex) {
			SysLog.logThrowable(ex);
		}
	}
	
	/**
	 * 
	 * @methodtype factory
	 * @methodproperties composed
	 */
	protected Message createMessage(EmailAddress from, EmailAddress to,
			EmailAddress bcc, String subject, String body)
			throws MessagingException, AddressException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from.asString()));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to.asString()));
		
		if (bcc != EmailAddress.NONE) {
			msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc.asString()));
		}

		msg.setSubject(subject);
		msg.setContent(createMultipart(body));
		return msg;
	}

	/**
	 * 
	 * @methodtype factory
	 * @methodproperties primitive, hook
	 */
	protected Multipart createMultipart(String body) throws MessagingException {
		Multipart mp = new MimeMultipart();
		BodyPart textPart = new MimeBodyPart();
		textPart.setText(body); // sets type to "text/plain"
		mp.addBodyPart(textPart);
		return mp;
	}
}
