package org.wahlzeit.services;

/*
 * Just like MockEmailServer, but does zero organization like Message creation etc.
 * Could be set through AbstractEmailServer.setInstance(new NullEmailServer());
 */
public class NullEmailServer implements EmailServer{
	/**
	 * @methodtype command
	 * @methodproperties primitive, hook
	 */
	@Override
	public void sendEmail(EmailAddress from, EmailAddress to, String subject,
			String body) {
		// do nothing
		
	}
	
	/**
	 * @methodtype command
	 * @methodproperties primitive, hook
	 */
	@Override
	public void sendEmail(EmailAddress from, EmailAddress to, EmailAddress bcc,
			String subject, String body) {
		// do nothing
		
	}

}
