package com.boshi.action.news.seniorhighschool.recruitstudentsnews;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.SeniorHighSchoolRecruitStudentsNews;

public class ShowSeniorHighSchoolRecruitStudentsNewsAction extends SuperShowNewsContentAction {

	private static final long					serialVersionUID	= 1L;
	private SeniorHighSchoolRecruitStudentsNews	content				= new SeniorHighSchoolRecruitStudentsNews();

	public String execute() {
		Object obj = super.show(SeniorHighSchoolRecruitStudentsNews.class);
		if (obj != null) {
			content = (SeniorHighSchoolRecruitStudentsNews) obj;
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
		return super.change(SeniorHighSchoolRecruitStudentsNews.class, content);
	}

	public SeniorHighSchoolRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolRecruitStudentsNews content) {
		this.content = content;
	}

}
