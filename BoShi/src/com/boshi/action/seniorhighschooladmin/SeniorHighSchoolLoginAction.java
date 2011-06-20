package com.boshi.action.seniorhighschooladmin;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.SeniorHighSchoolAdminUser;

public class SeniorHighSchoolLoginAction extends SuperLoginAndRegisterAction {

	private static final long			serialVersionUID	= 1L;
	private SeniorHighSchoolAdminUser	user				= new SeniorHighSchoolAdminUser();

	public String login() {
		return super.login(SeniorHighSchoolAdminUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public SeniorHighSchoolAdminUser getUser() {
		return user;
	}

	public void setUser(SeniorHighSchoolAdminUser user) {
		this.user = user;
	}

}
