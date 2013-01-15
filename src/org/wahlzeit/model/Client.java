package org.wahlzeit.model;

import org.wahlzeit.services.EmailAddress;

/*
 * It's a 2 step hierarchy of roles, because a client is either a guest or user. Therefore a Client has to play a role. 
 * Users however could exist without playing any role. A possible UserRole is Moderator or Administrator or both.  
 * These 2 different kinds of roles are implemented through 2 steps
 * 
 */

public abstract class Client {

	/**
	 * 
	 */
	protected ClientRole role;
	
	/**
	 * 
	 */
	public ClientRole getRole(){
		return role;
	}
	
	/**
	 * 
	 */
	public void setRole(ClientRole role){
		this.role=role;
		role.setCore(this);
	}
	
	/**
	 * 
	 */
	protected abstract void initialize(AccessRights myRights, EmailAddress myEmailAddress);
	
	public abstract AccessRights getRights();
	
	public abstract void setRights(AccessRights newRights);
	
	public abstract boolean hasRights(AccessRights otherRights);
	
	public abstract boolean hasGuestRights();
	
	public abstract boolean hasUserRights();
	
	public abstract boolean hasModeratorRights();
	
	public abstract boolean hasAdministratorRights();
	
	public abstract EmailAddress getEmailAddress();
	
	public abstract void setEmailAddress(EmailAddress newEmailAddress);
}
