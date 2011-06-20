package com.boshi.action.user.psychoanalyst;

import com.boshi.action.user.SuperListUserAction;
import com.boshi.db.datamodel.user.PsychoanalystUser;

public class ListPsychoanalystUserAction extends SuperListUserAction {
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
		return super.remove(PsychoanalystUser.class);
	}
}
