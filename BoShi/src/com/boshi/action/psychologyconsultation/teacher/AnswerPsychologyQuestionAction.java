package com.boshi.action.psychologyconsultation.teacher;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datamodel.psychologyconsultation.QuestionAndAnswer;
import com.boshi.action.SuperAddAction;

public class AnswerPsychologyQuestionAction extends SuperAddAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private HttpServletRequest	servletRequest		= null;
	private QuestionAndAnswer	answer				= new QuestionAndAnswer();

	public String prepareAnswer() {
		String id = (String) servletRequest.getParameter("id");
		if (id == null)
			return "noID";
		try {
			answer = (QuestionAndAnswer) super.getDataCenterInterface().findObject(QuestionAndAnswer.class, Long.valueOf(id));
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

	public QuestionAndAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(QuestionAndAnswer answer) {
		this.answer = answer;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
}
