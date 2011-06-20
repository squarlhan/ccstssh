package com.boshi.action.news.collegenews;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.CollegeNews;

public class ShowCollegeNewsAction extends SuperShowNewsContentAction {

	private static final long	serialVersionUID	= 1L;
	private CollegeNews			content				= null;

	public String execute() {
		Object obj = super.show(CollegeNews.class);
		if (obj != null) {
			content = (CollegeNews) obj;
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
		return super.change(CollegeNews.class, content);
	}

	public CollegeNews getContent() {
		return content;
	}

	public void setContent(CollegeNews content) {
		this.content = content;
	}

}
