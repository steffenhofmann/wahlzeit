package org.wahlzeit.services;

public interface EmailServer {
	public void sendEmail(EmailAddress from, EmailAddress to, String subject, String body);
	public void sendEmail(EmailAddress from, EmailAddress to, EmailAddress bcc, String subject, String body);
}
