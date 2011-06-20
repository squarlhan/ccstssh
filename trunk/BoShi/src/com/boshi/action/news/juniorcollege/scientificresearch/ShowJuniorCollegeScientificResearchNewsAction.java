package com.boshi.action.news.juniorcollege.scientificresearch;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeScientificResearchNews;

public class ShowJuniorCollegeScientificResearchNewsAction extends SuperShowNewsContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeScientificResearchNews	content				= null;

	public String execute() {
		Object obj = super.show(JuniorCollegeScientificResearchNews.class);
		if (obj != null) {
			content = (JuniorCollegeScientificResearchNews) obj;
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
		return super.change(JuniorCollegeScientificResearchNews.class, content);
	}

	public JuniorCollegeScientificResearchNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeScientificResearchNews content) {
		this.content = content;
	}

}
