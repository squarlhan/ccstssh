package com.boshi.action.news.seniorhighschool.notices;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.SeniorNotices;;

public class AddSeniorNoticesAction extends SuperAddNewsAction {

	private static final long				serialVersionUID	= 1L;
	private SeniorNotices	content				= new SeniorNotices();

	public String execute() {
		return super.add(content);
	}

	public SeniorNotices getContent() {
		return content;
	}

	public void setContent(SeniorNotices content) {
		this.content = content;
	}

}
