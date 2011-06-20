package com.boshi.action.news.juniorcollege.studentswork;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCStudentsWorks;

public class AddStudentsWorkAction extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private JuniorCStudentsWorks	content				= new JuniorCStudentsWorks();

	public String execute() {
		return super.add(content);
	}

	public JuniorCStudentsWorks getContent() {
		return content;
	}

	public void setContent(JuniorCStudentsWorks content) {
		this.content = content;
	}
}
