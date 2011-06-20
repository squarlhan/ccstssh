package com.boshi.action.news.juniorcollege.fsfellow;

import com.boshi.action.news.SuperListNewsAction;
import com.boshi.db.datamodel.news.FSFellow;

public class ListFSFellowAction extends SuperListNewsAction{

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
		return super.remove(FSFellow.class);
	}
}
