package com.boshi.action.news.juniorhighschool.notices;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorNotices;;

public class AddJuniorNoticesAction extends SuperAddNewsAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorNotices	content				= new JuniorNotices();

	public String execute() {
		return super.add(content);
	}

	public JuniorNotices getContent() {
		return content;
	}

	public void setContent(JuniorNotices content) {
		this.content = content;
	}

}
