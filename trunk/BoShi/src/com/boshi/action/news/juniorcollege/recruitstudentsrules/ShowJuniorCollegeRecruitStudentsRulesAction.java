package com.boshi.action.news.juniorcollege.recruitstudentsrules;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeRecruitStudentsRules;

public class ShowJuniorCollegeRecruitStudentsRulesAction extends SuperShowNewsContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeRecruitStudentsRules	content				= new JuniorCollegeRecruitStudentsRules();

	public String execute() {
		Object obj = super.show(JuniorCollegeRecruitStudentsRules.class);
		if (obj != null) {
			content = (JuniorCollegeRecruitStudentsRules) obj;
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
		return super.change(JuniorCollegeRecruitStudentsRules.class, content);
	}

	public JuniorCollegeRecruitStudentsRules getContent() {
		return content;
	}

	public void setContent(JuniorCollegeRecruitStudentsRules content) {
		this.content = content;
	}

}
