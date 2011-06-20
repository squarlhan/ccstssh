package com.boshi.action.user.juniorhighschooladmin;

import com.boshi.db.datamodel.user.JuniorHighSchoolAdminUser;
import com.boshi.action.user.SuperAddUserAction;

public class AddJuniorHighSchoolAdminUserAction extends SuperAddUserAction {

	private static final long	serialVersionUID	= 1L;
	private JuniorHighSchoolAdminUser	user				= new JuniorHighSchoolAdminUser();

	public String execute() {
		return super.add(user);
	}

	public JuniorHighSchoolAdminUser getUser() {
		return user;
	}

	public void setUser(JuniorHighSchoolAdminUser user) {
		this.user = user;
	}

}
