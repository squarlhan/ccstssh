package com.boshi.action.news.juniorcollege.envirmanagement;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.EnvirManagement;

public class ShowEnvirManagementAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private EnvirManagement	content				= new EnvirManagement();

	public String execute() {
		Object obj = super.show(EnvirManagement.class);
		if (obj != null) {
			content = (EnvirManagement) obj;
			return SUCCESS;
		}
		return ERROR;
	}

	public String toChange() {
		if (execute().equals(SUCCESS))
			return "toChange";
		return ERROR;
	}

	public String change() {
		return super.change(EnvirManagement.class, content);
	}

	public EnvirManagement getContent() {
		return content;
	}

	public void setContent(EnvirManagement content) {
		this.content = content;
	}
}
