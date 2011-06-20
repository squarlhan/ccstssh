package com.boshi.action.news.juniorcollege.teachingactivity;

import com.boshi.db.datamodel.news.JuniorCollegeTeachingActivityNews;
import com.boshi.action.news.SuperAddNewsAction;

public class AddJuniorCollegeTeachingActivityNewsAction extends SuperAddNewsAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeTeachingActivityNews	content				= new JuniorCollegeTeachingActivityNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeTeachingActivityNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeTeachingActivityNews content) {
		this.content = content;
	}

}
