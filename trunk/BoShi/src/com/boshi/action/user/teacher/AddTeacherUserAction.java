package com.boshi.action.user.teacher;

import com.boshi.db.datamodel.user.TeacherUser;
import com.boshi.action.user.SuperAddUserAction;

public class AddTeacherUserAction extends SuperAddUserAction {

	private static final long	serialVersionUID	= 1L;
	private TeacherUser			user				= new TeacherUser();

	public String execute() {
		return super.add(user);
	}

	public TeacherUser getUser() {
		return user;
	}

	public void setUser(TeacherUser user) {
		this.user = user;
	}

}
