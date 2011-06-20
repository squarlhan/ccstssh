package com.boshi.action.news.juniorcollege.recruitstudentsnews;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeRecruitStudentsNews;

public class ShowJuniorCollegeRecruitStudentsNewsAction extends SuperShowNewsContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeRecruitStudentsNews	content				= new JuniorCollegeRecruitStudentsNews();

	public String execute() {
		Object obj = super.show(JuniorCollegeRecruitStudentsNews.class);
		if (obj != null) {
			content = (JuniorCollegeRecruitStudentsNews) obj;
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
		return super.change(JuniorCollegeRecruitStudentsNews.class, content);
	}

	public JuniorCollegeRecruitStudentsNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeRecruitStudentsNews content) {
		this.content = content;
	}

}
