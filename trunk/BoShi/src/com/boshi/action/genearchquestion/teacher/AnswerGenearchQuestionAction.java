package com.boshi.action.genearchquestion.teacher;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datamodel.genearchquestion.GenearchQuestion;
import com.boshi.action.SuperAddAction;

public class AnswerGenearchQuestionAction extends SuperAddAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private HttpServletRequest	servletRequest		= null;
	private GenearchQuestion	answer				= new GenearchQuestion();

	public String prepareAnswer() {
		String id = (String) servletRequest.getParameter("id");
		if (id == null)
			return "noID";
		try {
			answer = (GenearchQuestion) super.getDataCenterInterface().findObject(GenearchQuestion.class, Long.valueOf(id));
			return "toAnswer";
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "noID";
	}

	public String execute() {
		try {
			super.getDataCenterInterface().addAnswerObject(answer);
			return SUCCESS;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return INPUT;
	}

	public GenearchQuestion getAnswer() {
		return answer;
	}

	public void setAnswer(GenearchQuestion answer) {
		this.answer = answer;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
}
