package com.boshi.action.news.juniorhighschool.recruitstudentsnews;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorHighSchoolRecruitStudentsNews;

public class ShowJuniorHighSchoolRecruitStudentsNewsAction extends SuperShowNewsContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorHighSchoolRecruitStudentsNews	content				= new JuniorHighSchoolRecruitStudentsNews();

	public String execute() {
		Object obj = super.show(JuniorHighSchoolRecruitStudentsNews.class);
		if (obj != null) {
			content = (JuniorHighSchoolRecruitStudentsNews) obj;
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
		return super.change(JuniorHighSchoolRecruitStudentsNews.class, content);
	}

	public JuniorHighSchoolRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolRecruitStudentsNews content) {
		this.content = content;
	}

}
