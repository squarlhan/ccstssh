package com.boshi.action.news.juniorcollege.studentmanagement;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.StudentManagement;


public class AddStudentManagement extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private StudentManagement	content				= new StudentManagement();

	public String execute() {
		return super.add(content);
	}

	public StudentManagement getContent() {
		return content;
	}

	public void setContent(StudentManagement content) {
		this.content = content;
	}
}
