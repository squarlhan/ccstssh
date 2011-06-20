package com.boshi.action.news.juniorcollege.recruitstudentsnews;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeRecruitStudentsNews;

public class AddJuniorCollegeRecruitStudentsNewsAction extends SuperAddNewsAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeRecruitStudentsNews	content				= new JuniorCollegeRecruitStudentsNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeRecruitStudentsNews content) {
		this.content = content;
	}

}
