package com.boshi.action.news.seniorhighschool.notices;

import com.boshi.action.news.SuperListNewsAction;
import com.boshi.db.datamodel.news.SeniorNotices;;

public class ListSeniorNoticesAction extends SuperListNewsAction {
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
		return super.remove(SeniorNotices.class);
	}
}
