package com.boshi.action.psychologyconsultation.student;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.StudentUser;

public class StudentLoginAndRegisterAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private StudentUser			user				= new StudentUser();

	public String login() {
		return super.login(StudentUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public String register() {
		return super.register(user);
	}

	public StudentUser getUser() {
		return user;
	}

	public void setUser(StudentUser user) {
		this.user = user;
	}

}
