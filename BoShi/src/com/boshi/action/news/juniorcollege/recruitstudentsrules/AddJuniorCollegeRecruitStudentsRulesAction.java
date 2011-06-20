package com.boshi.action.news.juniorcollege.recruitstudentsrules;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeRecruitStudentsRules;

public class AddJuniorCollegeRecruitStudentsRulesAction extends SuperAddNewsAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeRecruitStudentsRules	content				= new JuniorCollegeRecruitStudentsRules();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeRecruitStudentsRules getContent() {
		return content;
	}

	public void setContent(JuniorCollegeRecruitStudentsRules content) {
		this.content = content;
	}

}
