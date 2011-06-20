package com.boshi.action.psychologyconsultation.student;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datamodel.psychologyconsultation.QuestionAndAnswer;
import com.boshi.action.SuperAddAction;

public class AddPsychologyQuestionAction extends SuperAddAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private QuestionAndAnswer	question			= new QuestionAndAnswer();
	private HttpServletRequest	servletRequest		= null;

	public String execute() {
		String studentName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (studentName != null && power != null && power.equals("student")) {
			try {
				super.getDataCenterInterface().addQuestionObject(question, studentName);
				super.addActionMessage(super.getText("add.success"));
				return SUCCESS;
			} catch (Exception e) {
				super.addActionMessage(super.getText("add.error"));
				return INPUT;
			}
		}
		return "toLogin";

	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public QuestionAndAnswer getQuestion() {
		return question;
	}

	public void setQuestion(QuestionAndAnswer question) {
		this.question = question;
	}

}
