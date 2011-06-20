package com.boshi.action.news.juniorcollege.envirmanagement;

import com.boshi.action.news.SuperListNewsAction;
import com.boshi.db.datamodel.news.EnvirManagement;

public class ListEnvirManagementAction extends SuperListNewsAction {

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
		return super.remove(EnvirManagement.class);
	}
}
