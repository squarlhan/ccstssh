package com.boshi.action.news.juniorcollege.teachingmanage;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeTeachingManageNews;

public class ShowJuniorCollegeTeachingManageNewsAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeTeachingManageNews	content				= null;

	public String execute() {
		Object obj = super.show(JuniorCollegeTeachingManageNews.class);
		if (obj != null) {
			content = (JuniorCollegeTeachingManageNews) obj;
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
		return super.change(JuniorCollegeTeachingManageNews.class, content);
	}

	public JuniorCollegeTeachingManageNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeTeachingManageNews content) {
		this.content = content;
	}

}
