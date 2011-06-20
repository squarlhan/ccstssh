package com.boshi.action.psychologyconsultation.teacher;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.PsychoanalystUser;

public class PsychologyLoginAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private PsychoanalystUser	user				= new PsychoanalystUser();

	public String login() {
		return super.login(PsychoanalystUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public PsychoanalystUser getUser() {
		return user;
	}

	public void setUser(PsychoanalystUser user) {
		this.user = user;
	}
}
