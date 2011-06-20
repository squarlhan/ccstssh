package com.boshi.action.news.juniorcollege.teachingmanage;

import com.boshi.db.datamodel.news.JuniorCollegeTeachingManageNews;
import com.boshi.action.news.SuperAddNewsAction;

public class AddJuniorCollegeTeachingManageNewsAction extends SuperAddNewsAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeTeachingManageNews	content				= new JuniorCollegeTeachingManageNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeTeachingManageNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeTeachingManageNews content) {
		this.content = content;
	}

}
