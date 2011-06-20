package com.boshi.action.news.juniorcollege.scientificresearch;

import com.boshi.db.datamodel.news.JuniorCollegeScientificResearchNews;
import com.boshi.action.news.SuperAddNewsAction;

public class AddJuniorCollegeScientificResearchNewsAction extends SuperAddNewsAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeScientificResearchNews	content				= new JuniorCollegeScientificResearchNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeScientificResearchNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeScientificResearchNews content) {
		this.content = content;
	}

}
