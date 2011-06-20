package com.boshi.action.psychologyconsultation.teacher;

import com.boshi.action.SuperListAction;

public class ListNotAnswerPsychologyQuestionAction extends SuperListAction {
	private static final long	serialVersionUID	= 1L;

	public String execute() {
		super.superExecute(null);
		return SUCCESS;
	}

	public String pagination() {
		super.superPagination(null);
		return SUCCESS;
	}
}
