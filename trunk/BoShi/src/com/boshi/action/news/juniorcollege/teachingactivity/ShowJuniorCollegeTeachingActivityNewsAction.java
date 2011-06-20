package com.boshi.action.news.juniorcollege.teachingactivity;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeTeachingActivityNews;

public class ShowJuniorCollegeTeachingActivityNewsAction extends SuperShowNewsContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeTeachingActivityNews	content				= null;

	public String execute() {
		Object obj = super.show(JuniorCollegeTeachingActivityNews.class);
		if (obj != null) {
			content = (JuniorCollegeTeachingActivityNews) obj;
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
		return super.change(JuniorCollegeTeachingActivityNews.class, content);
	}

	public JuniorCollegeTeachingActivityNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeTeachingActivityNews content) {
		this.content = content;
	}

}
