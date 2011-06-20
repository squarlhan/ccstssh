package com.boshi.action.user.seniorhighschooladmin;

import com.boshi.action.user.SuperListUserAction;
import com.boshi.db.datamodel.user.SeniorHighSchoolAdminUser;

public class ListSeniorHighSchoolAdminUserAction extends SuperListUserAction {
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
		return super.remove(SeniorHighSchoolAdminUser.class);
	}
}
