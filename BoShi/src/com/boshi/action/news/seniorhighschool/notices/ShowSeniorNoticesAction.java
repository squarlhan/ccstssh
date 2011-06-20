package com.boshi.action.news.seniorhighschool.notices;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.SeniorNotices;

public class ShowSeniorNoticesAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private SeniorNotices	content				= new SeniorNotices();

	public String execute() {
		Object obj = super.show(SeniorNotices.class);
		if (obj != null) {
			content = (SeniorNotices) obj;
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
		return super.change(SeniorNotices.class, content);
	}

	public SeniorNotices getContent() {
		return content;
	}

	public void setContent(SeniorNotices content) {
		this.content = content;
	}

}
