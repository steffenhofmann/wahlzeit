package org.wahlzeit.model;


import junit.framework.TestCase;

public class UserTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(UserStatusTest.class);
	}

	public UserTest(String name) {
		super(name);
	}

	public void testGetUserRole(){
		Client client = ClientCore.createWithUserRole();
		User user = (User)client.getRole();
		
		Moderator mod = new Moderator(user);
		Administrator admin = new Administrator(user);
		
		assertTrue(user.getUserRole(Moderator.ROLE_NAME) == null);
		assertTrue(user.getUserRole(Administrator.ROLE_NAME) == null);
		
		user.addUserRole(mod);
		
		assertTrue(user.getUserRole(Moderator.ROLE_NAME) == mod);
		assertTrue(user.getUserRole(Administrator.ROLE_NAME) == null);
		
		user.addUserRole(admin);
		
		assertTrue(user.getUserRole(Moderator.ROLE_NAME) == mod);
		assertTrue(user.getUserRole(Administrator.ROLE_NAME) == admin);
	}
	
	public void testRemoveUserRole(){
		Client client = ClientCore.createWithUserRole();
		User user = (User)client.getRole();
		
		Moderator mod = new Moderator(user);
		Administrator admin = new Administrator(user);
		
		assertFalse(user.removeUserRole(mod));
		assertFalse(user.removeUserRole(admin));
		
		user.addUserRole(mod);
		
		assertTrue(user.removeUserRole(mod));
		assertFalse(user.removeUserRole(admin));
		
		user.addUserRole(admin);
		
		assertFalse(user.removeUserRole(mod));
		assertTrue(user.removeUserRole(admin));
		
		user.addUserRole(mod);
		user.addUserRole(admin);
		
		assertTrue(user.removeUserRole(mod));
		assertTrue(user.removeUserRole(admin));
	}
}
