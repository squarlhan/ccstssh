package com.boshi.action.news.juniorcollege.studentswork;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCStudentsWorks;

public class ShowStudentsWorkAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCStudentsWorks	content				= new JuniorCStudentsWorks();

	public String execute() {
		Object obj = super.show(JuniorCStudentsWorks.class);
		if (obj != null) {
			content = (JuniorCStudentsWorks) obj;
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
		return super.change(JuniorCStudentsWorks.class, content);
	}

	public JuniorCStudentsWorks getContent() {
		return content;
	}

	public void setContent(JuniorCStudentsWorks content) {
		this.content = content;
	}
}
