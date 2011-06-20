package com.boshi.action.news.juniorhighschool.recruitstudentsnews;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorHighSchoolRecruitStudentsNews;

public class AddJuniorHighSchoolRecruitStudentsNewsAction extends SuperAddNewsAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorHighSchoolRecruitStudentsNews	content				= new JuniorHighSchoolRecruitStudentsNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorHighSchoolRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolRecruitStudentsNews content) {
		this.content = content;
	}

}
