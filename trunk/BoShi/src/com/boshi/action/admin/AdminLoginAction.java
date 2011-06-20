package com.boshi.action.admin;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.AdminUser;

public class AdminLoginAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private AdminUser			user				= new AdminUser();

	public String login() {
		return super.login(AdminUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

}
