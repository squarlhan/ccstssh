package com.boshi.action.news.juniorcollege.fsfellow;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.FSFellow;

public class ShowFSFellowAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private FSFellow	content				= new FSFellow();

	public String execute() {
		Object obj = super.show(FSFellow.class);
		if (obj != null) {
			content = (FSFellow) obj;
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
		return super.change(FSFellow.class, content);
	}

	public FSFellow getContent() {
		return content;
	}

	public void setContent(FSFellow content) {
		this.content = content;
	}
}
