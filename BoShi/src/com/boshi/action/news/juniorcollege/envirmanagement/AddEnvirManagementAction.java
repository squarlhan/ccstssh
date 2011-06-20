package com.boshi.action.news.juniorcollege.envirmanagement;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.EnvirManagement;

public class AddEnvirManagementAction extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private EnvirManagement	content				= new EnvirManagement();

	public String execute() {
		return super.add(content);
	}

	public EnvirManagement getContent() {
		return content;
	}

	public void setContent(EnvirManagement content) {
		this.content = content;
	}
}
