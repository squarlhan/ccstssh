package com.boshi.action.user.juniorhighschooladmin;

import com.boshi.action.user.SuperListUserAction;
import com.boshi.db.datamodel.user.JuniorHighSchoolAdminUser;

public class ListJuniorHighSchoolAdminUserAction extends SuperListUserAction {
	private static final long	serialVersionUID	= 1L;

	public String execute() {
		super.superExecute(null);
		return SUCCESS;
	}

	public String pagination() {
		super.superPagination(null);
		return SUCCESS;
	}

	public String remove() {
		return super.remove(JuniorHighSchoolAdminUser.class);
	}
}
