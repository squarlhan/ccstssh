package com.boshi.action.genearchquestion.genearch;

import com.boshi.action.SuperListAction;

public class ListGenearchQuestionAndAnswerAction extends SuperListAction {
	private static final long	serialVersionUID	= 1L;

	public String execute() {
		String genearchName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (genearchName != null && power != null && power.equals("genearch")) {
			super.superExecute(new String[] { genearchName });
			return SUCCESS;
		}
		return "toLogin";
	}

	public String pagination() {
		String genearchName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (genearchName != null && power != null && power.equals("genearch")) {
			super.superPagination(new String[] { genearchName });
			return SUCCESS;
		}
		return "toLogin";
	}
}
