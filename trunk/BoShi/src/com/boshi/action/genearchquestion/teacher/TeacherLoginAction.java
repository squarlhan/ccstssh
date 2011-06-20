package com.boshi.action.genearchquestion.teacher;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.TeacherUser;

public class TeacherLoginAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private TeacherUser			user				= new TeacherUser();

	public String login() {
		return super.login(TeacherUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public TeacherUser getUser() {
		return user;
	}

	public void setUser(TeacherUser user) {
		this.user = user;
	}

}
