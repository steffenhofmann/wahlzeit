package org.wahlzeit.services;

import java.util.*;

/*
 * Der Mailserver wird nun nicht mehr als Singleton erstellt, sondern über den Manager/Factory erzeugt.
 * Eventuell im parallelen Betrieb hilfreich!
 * (Das Beste für 2:06AM :/)
 */

public class EmailServerManager {
	private static EmailServerManager instance;
	
	private LinkedList<EmailServer> emailservers;
	private int maxInstances = 1;
	
	
	
	private EmailServerManager(){
		emailservers = new LinkedList<EmailServer>();
	}
	
	public static EmailServerManager getInstance(){
		if(instance == null){
			instance = new EmailServerManager();
		}
		
		return instance;
	}
	
	public EmailServer get() {
		if(emailservers.size() <= maxInstances){
			EmailServer e;
			if (SysLog.isInProductionMode()) {
				e = new DefaultEmailServer();
			} else {
				e = new MockEmailServer();
			}
			
			emailservers.add(e);
			return e;
		}
		return null;
	}
	
	public EmailServer getMock() {
		if(emailservers.size() <= maxInstances){
			EmailServer e = new MockEmailServer();
			
			emailservers.add(e);
			return e;
		}
		return null;
	}
	
	public void release(EmailServer e) {
		emailservers.remove(e);
	}
	
	
}
