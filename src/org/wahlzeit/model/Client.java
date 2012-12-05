/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.wahlzeit.services.*;

/**
 * A Client uses the system. It is an abstract superclass.
 * This package defines guest, user, moderator, and administrator clients.
 * 
 * @author dirkriehle
 *
 */
public abstract class Client {
	
	/**
	 * 
	 */
	protected AccessRights rights = AccessRights.NONE;
	
	/**
	 * 
	 */
	protected EmailAddress emailAddress = EmailAddress.NONE;
	
	/**
	 * 
	 */
	protected Client() {
		// do nothing
	}
	
	/**
	 * @methodtype initialization
	 */
	protected void initialize(AccessRights myRights, EmailAddress myEmailAddress) {
		//require
		assert (myRights != null && myEmailAddress != null);
		
		rights = myRights;
		setEmailAddress(myEmailAddress);
		
		//ensure
		assert(getRights()==myRights && getEmailAddress()==myEmailAddress);
		
		//invariant
		assertInvariant();
	}

	/**
	 * @methodtype get
	 */
	public AccessRights getRights() {
		return rights;
	}
	
	/**
	 * @methodtype set
	 */
	public void setRights(AccessRights newRights) {
		//require
		assert(newRights != null);
		
		rights = newRights;
		
		//ensure
		assert(getRights() == newRights);
		
		//invariant
		assertInvariant();
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasRights(AccessRights otherRights) {
		//require
		assert(otherRights != null && rights != null);
		
		boolean ret = AccessRights.hasRights(rights, otherRights); 
		
		//invariant
		assertInvariant();
		return ret;
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasGuestRights() {
		return hasRights(AccessRights.GUEST);
	}
	
	/**
	 * 
	 */
	public boolean hasUserRights() {
		return hasRights(AccessRights.USER);
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasModeratorRights() {
		return hasRights(AccessRights.MODERATOR);
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasAdministratorRights() {
		return hasRights(AccessRights.ADMINISTRATOR);
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setEmailAddress(EmailAddress newEmailAddress) {
		//require
		assert(newEmailAddress != null);
		
		emailAddress = newEmailAddress;
		
		//ensure
		assert(getEmailAddress() == newEmailAddress);
		
		//invariant
		assertInvariant();
	}
	
	/**
	 * 
	 */
	private void assertInvariant() {
		//ensure valid state of class: rights/email set 
		assert(rights != null && ((rights == AccessRights.NONE)||(rights == AccessRights.GUEST)||(rights == AccessRights.USER)||(rights == AccessRights.MODERATOR)||(rights == AccessRights.ADMINISTRATOR)));
		assert(emailAddress != null);
	}
	
}
