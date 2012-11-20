package org.wahlzeit.services;

import javax.mail.*;

/*
 * Does everything like a standard DefaultEmailServer, just doesn't trigger send
 */
public class MockEmailServer extends AbstractEmailServer{
	/**
	 * @methodtype command
	 * @methodproperties primitive, hook
	 */
	protected void doSendEmail(Message msg) {
		SysLog.logInfo("pretending to send email...");
	}
	
}
