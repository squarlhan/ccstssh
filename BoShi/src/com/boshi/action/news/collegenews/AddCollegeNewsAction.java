package com.boshi.action.news.collegenews;

import com.boshi.db.datamodel.news.CollegeNews;
import com.boshi.action.news.SuperAddNewsAction;

public class AddCollegeNewsAction extends SuperAddNewsAction {

	private static final long	serialVersionUID	= 1L;
	private CollegeNews			content				= new CollegeNews();

	public String execute() {
		return super.add(content);
	}

	public CollegeNews getContent() {
		return content;
	}

	public void setContent(CollegeNews content) {
		this.content = content;
	}

}
