package com.boshi.action.juniorhighschooladmin;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.JuniorHighSchoolAdminUser;

public class JuniorHighSchoolLoginAction extends SuperLoginAndRegisterAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorHighSchoolAdminUser	user				= new JuniorHighSchoolAdminUser();

	public String login() {
		return super.login(JuniorHighSchoolAdminUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public JuniorHighSchoolAdminUser getUser() {
		return user;
	}

	public void setUser(JuniorHighSchoolAdminUser user) {
		this.user = user;
	}

}
