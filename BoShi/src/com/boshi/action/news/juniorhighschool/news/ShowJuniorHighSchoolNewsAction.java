package com.boshi.action.news.juniorhighschool.news;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorHighSchoolNews;

public class ShowJuniorHighSchoolNewsAction extends SuperShowNewsContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorHighSchoolNews	content				= new JuniorHighSchoolNews();

	public String execute() {
		Object obj = super.show(JuniorHighSchoolNews.class);
		if (obj != null) {
			content = (JuniorHighSchoolNews) obj;
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
		return super.change(JuniorHighSchoolNews.class, content);
	}

	public JuniorHighSchoolNews getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolNews content) {
		this.content = content;
	}

}
