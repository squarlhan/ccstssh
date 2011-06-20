package com.boshi.action.schoolfellowrecord;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datamodel.schoolfellowrecord.RecordContent;
import com.boshi.action.SuperAddAction;

public class AddSchoolfellowRecordAction extends SuperAddAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private RecordContent		content				= new RecordContent();
	private HttpServletRequest	servletRequest		= null;

	public String execute() {
		String schoolfellowRecordUserName = (String) servletRequest.getSession().getAttribute("name");
		String power = (String) servletRequest.getSession().getAttribute("power");
		if (schoolfellowRecordUserName != null && power != null && power.equals("schoolfellowUser"))
			content.getSchoolfellowUser().setName(schoolfellowRecordUserName);
		try {
			super.getDataCenterInterface().addQuestionObject(content, null);
			super.addActionMessage(super.getText("add.success"));
			return SUCCESS;
		} catch (Exception e) {
			super.addActionMessage(super.getText("add.error"));
		}
		return INPUT;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public RecordContent getContent() {
		return content;
	}

	public void setContent(RecordContent content) {
		this.content = content;
	}

}
