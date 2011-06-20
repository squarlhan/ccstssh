package com.boshi.action.news.juniorhighschool.notices;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorNotices;

public class ShowJuniorNoticesAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorNotices	content				= new JuniorNotices();

	public String execute() {
		Object obj = super.show(JuniorNotices.class);
		if (obj != null) {
			content = (JuniorNotices) obj;
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
		return super.change(JuniorNotices.class, content);
	}

	public JuniorNotices getContent() {
		return content;
	}

	public void setContent(JuniorNotices content) {
		this.content = content;
	}

}
