package com.boshi.action.news.seniorhighschool.recruitstudentsnews;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.SeniorHighSchoolRecruitStudentsNews;

public class AddSeniorHighSchoolRecruitStudentsNewsAction extends SuperAddNewsAction {

	private static final long					serialVersionUID	= 1L;
	private SeniorHighSchoolRecruitStudentsNews	content				= new SeniorHighSchoolRecruitStudentsNews();

	public String execute() {
		return super.add(content);
	}

	public SeniorHighSchoolRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolRecruitStudentsNews content) {
		this.content = content;
	}

}
