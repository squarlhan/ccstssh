package com.boshi.action.news.seniorhighschool.news;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.SeniorHighSchoolNews;

public class AddSeniorHighSchoolNewsAction extends SuperAddNewsAction {

	private static final long		serialVersionUID	= 1L;
	private SeniorHighSchoolNews	content				= new SeniorHighSchoolNews();

	public String execute() {
		return super.add(content);
	}

	public SeniorHighSchoolNews getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolNews content) {
		this.content = content;
	}

}
