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

public class ClientCore extends Client{

	/**
	 * 
	 */
	protected AccessRights rights = AccessRights.NONE;
	
	/**
	 * 
	 */
	protected EmailAddress emailAddress = EmailAddress.EMPTY;
	
	/**
	 * 
	 */
	private ClientCore(ClientRole role) {
		setRole(role);
	}
	
	/**
	 * 
	 */
	public static Client createWithGuestRole(){
		return new ClientCore(new Guest());
	}
	
	/**
	 * 
	 */
	public static Client createWithUserRole(){
		return new ClientCore(new User());
	}
	
	/**
	 * 
	 */
	public static Client createWithUserRole(String myName, String myPassword, String myEmailAddress, long vc){
		return new ClientCore(new User(myName, myPassword, myEmailAddress, vc));
	}
	
	/**
	 * 
	 */
	public static Client createWithUserRole(String myName, String myPassword, EmailAddress myEmailAddress, long vc){
		return new ClientCore(new User(myName, myPassword, myEmailAddress, vc));
	}
	
	/**
	 * @methodtype initialization
	 */
	protected void initialize(AccessRights myRights, EmailAddress myEmailAddress) {
		rights = myRights;
		setEmailAddress(myEmailAddress);
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
		rights = newRights;
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasRights(AccessRights otherRights) {
		return AccessRights.hasRights(rights, otherRights);
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
	public boolean hasModeratorRights
	() {
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
		emailAddress = newEmailAddress;
	}
	
}
