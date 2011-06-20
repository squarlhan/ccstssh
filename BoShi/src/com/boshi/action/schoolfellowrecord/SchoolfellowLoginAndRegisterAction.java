package com.boshi.action.schoolfellowrecord;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.SchoolfellowUser;

public class SchoolfellowLoginAndRegisterAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private SchoolfellowUser	user				= new SchoolfellowUser();

	public String login() {
		return super.login(SchoolfellowUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public String register() {
		return super.register(user);
	}

	public SchoolfellowUser getUser() {
		return user;
	}

	public void setUser(SchoolfellowUser user) {
		this.user = user;
	}

}
