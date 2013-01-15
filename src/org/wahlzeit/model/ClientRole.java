package org.wahlzeit.model;

import org.wahlzeit.services.EmailAddress;

public abstract class ClientRole extends Client{
	
	/**
	 * 
	 */
	protected Client core = null;
	
	/**
	 * 
	 */
	public void setCore(Client client){
		this.core = client;
	}
	
	/**
	 * 
	 */
	public Client getCore(){
		return core;
	}
	
	/**
	 * 
	 */
	protected void initialize(AccessRights myRights, EmailAddress myEmailAddress) {
		core.initialize(myRights, myEmailAddress);
	}

	/**
	 * 
	 */
	public AccessRights getRights() {
		return core.getRights();
	}

	/**
	 * 
	 */
	public void setRights(AccessRights newRights) {
		core.setRights(newRights);
	}

	/**
	 * 
	 */
	public boolean hasRights(AccessRights otherRights) {
		return core.hasRights(otherRights);
	}

	/**
	 * 
	 */
	public boolean hasGuestRights() {
		return core.hasGuestRights();
	}

	/**
	 * 
	 */
	public boolean hasUserRights() {
		return core.hasUserRights();
	}

	/**
	 * 
	 */
	public boolean hasModeratorRights() {
		return core.hasModeratorRights();
	}

	/**
	 * 
	 */
	public boolean hasAdministratorRights() {
		return core.hasAdministratorRights();
	}

	/**
	 * 
	 */
	public EmailAddress getEmailAddress() {
		return core.getEmailAddress();
	}
}
