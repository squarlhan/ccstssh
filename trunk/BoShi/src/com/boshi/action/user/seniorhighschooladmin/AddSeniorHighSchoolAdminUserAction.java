package com.boshi.action.user.seniorhighschooladmin;

import com.boshi.db.datamodel.user.SeniorHighSchoolAdminUser;
import com.boshi.action.user.SuperAddUserAction;

public class AddSeniorHighSchoolAdminUserAction extends SuperAddUserAction {

	private static final long			serialVersionUID	= 1L;
	private SeniorHighSchoolAdminUser	user				= new SeniorHighSchoolAdminUser();

	public String execute() {
		return super.add(user);
	}

	public SeniorHighSchoolAdminUser getUser() {
		return user;
	}

	public void setUser(SeniorHighSchoolAdminUser user) {
		this.user = user;
	}

}
