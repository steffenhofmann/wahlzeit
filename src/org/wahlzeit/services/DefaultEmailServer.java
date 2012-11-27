package org.wahlzeit.services;

import javax.mail.*;

/*
 * Default and ready to send
 */
public class DefaultEmailServer extends AbstractEmailServer{
	/**
	 * @methodtype command
	 * @methodproperties primitive, hook
	 */
	protected void doSendEmail(Message msg) throws Exception {
		Transport.send(msg);
	}
}
