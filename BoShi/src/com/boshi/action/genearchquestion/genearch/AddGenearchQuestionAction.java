package com.boshi.action.genearchquestion.genearch;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datamodel.genearchquestion.GenearchQuestion;
import com.boshi.action.SuperAddAction;

public class AddGenearchQuestionAction extends SuperAddAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private GenearchQuestion	question			= new GenearchQuestion();
	private HttpServletRequest	servletRequest		= null;

	public String execute() {
		String genearchName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (genearchName != null && power != null && power.equals("genearch")) {
			try {
				super.getDataCenterInterface().addQuestionObject(question, genearchName);
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

	public GenearchQuestion getQuestion() {
		return question;
	}

	public void setQuestion(GenearchQuestion question) {
		this.question = question;
	}

}
