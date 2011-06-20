package com.boshi.action.news.juniorhighschool.news;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorHighSchoolNews;

public class AddJuniorHighSchoolNewsAction extends SuperAddNewsAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorHighSchoolNews	content				= new JuniorHighSchoolNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorHighSchoolNews getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolNews content) {
		this.content = content;
	}

}
