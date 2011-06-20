package com.boshi.action.psychologyconsultation.student;

import com.boshi.action.SuperListAction;

public class ListPsychologyQuestionAndAnswerAction extends SuperListAction {
	private static final long	serialVersionUID	= 1L;

	public String execute() {
		String studentName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (studentName != null && power != null && power.equals("student")) {
			super.superExecute(new String[] { studentName });
			return SUCCESS;
		}
		return "toLogin";
	}

	public String pagination() {
		String studentName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (studentName != null && power != null && power.equals("student")) {
			super.superPagination(new String[] { studentName });
			return SUCCESS;
		}
		return "toLogin";
	}
}
