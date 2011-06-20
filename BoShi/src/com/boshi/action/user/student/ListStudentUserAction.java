package com.boshi.action.user.student;

import com.boshi.action.user.SuperListUserAction;
import com.boshi.db.datamodel.user.StudentUser;

public class ListStudentUserAction extends SuperListUserAction {
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
		return super.remove(StudentUser.class);
	}
}
