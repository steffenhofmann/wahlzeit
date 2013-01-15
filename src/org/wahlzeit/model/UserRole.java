package org.wahlzeit.model;

public abstract class UserRole extends User {
	protected User core = null;
	
	public abstract String getRoleName();
	
	public void setCore(User user){
		this.core = user;
	}
	
	public User getCore(){
		return core;
	}
}
