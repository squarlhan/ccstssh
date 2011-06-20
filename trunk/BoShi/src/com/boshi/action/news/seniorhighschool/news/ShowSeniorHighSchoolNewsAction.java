package com.boshi.action.news.seniorhighschool.news;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.SeniorHighSchoolNews;

public class ShowSeniorHighSchoolNewsAction extends SuperShowNewsContentAction {

	private static final long		serialVersionUID	= 1L;
	private SeniorHighSchoolNews	content				= new SeniorHighSchoolNews();

	public String execute() {
		Object obj = super.show(SeniorHighSchoolNews.class);
		if (obj != null) {
			content = (SeniorHighSchoolNews) obj;
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
		return super.change(SeniorHighSchoolNews.class, content);
	}

	public SeniorHighSchoolNews getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolNews content) {
		this.content = content;
	}

}
