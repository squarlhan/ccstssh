package com.boshi.action.news.juniorcollege.studentmanagement;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.StudentManagement;

public class ShowStudentManagement extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private StudentManagement	content				= new StudentManagement();

	public String execute() {
		Object obj = super.show(StudentManagement.class);
		if (obj != null) {
			content = (StudentManagement) obj;
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
		return super.change(StudentManagement.class, content);
	}

	public StudentManagement getContent() {
		return content;
	}

	public void setContent(StudentManagement content) {
		this.content = content;
	}
}
