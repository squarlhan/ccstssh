package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorHighSchoolExcellenceTeacher;

public class ShowJuniorHighSchoolExcellenceTeacherAction extends SuperShowAnotherContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorHighSchoolExcellenceTeacher	content				= new JuniorHighSchoolExcellenceTeacher();

	public String execute() {
		Object obj = super.show(JuniorHighSchoolExcellenceTeacher.class, "JuniorHighSchoolExcellenceTeacher");
		if (obj != null) {
			content = (JuniorHighSchoolExcellenceTeacher) obj;
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
		return super.change(JuniorHighSchoolExcellenceTeacher.class, content);
	}

	public JuniorHighSchoolExcellenceTeacher getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolExcellenceTeacher content) {
		this.content = content;
	}

}
