package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.SeniorHighSchoolExcellenceTeacher;

public class ShowSeniorHighSchoolExcellenceTeacherAction extends SuperShowAnotherContentAction {

	private static final long					serialVersionUID	= 1L;
	private SeniorHighSchoolExcellenceTeacher	content				= new SeniorHighSchoolExcellenceTeacher();

	public String execute() {
		Object obj = super.show(SeniorHighSchoolExcellenceTeacher.class, "SeniorHighSchoolExcellenceTeacher");
		if (obj != null) {
			content = (SeniorHighSchoolExcellenceTeacher) obj;
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
		return super.change(SeniorHighSchoolExcellenceTeacher.class, content);
	}

	public SeniorHighSchoolExcellenceTeacher getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolExcellenceTeacher content) {
		this.content = content;
	}

}
