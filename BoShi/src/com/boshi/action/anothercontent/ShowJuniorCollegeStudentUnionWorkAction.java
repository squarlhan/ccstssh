package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeStudentUnionWork;

public class ShowJuniorCollegeStudentUnionWorkAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeStudentUnionWork	content				= new JuniorCollegeStudentUnionWork();

	public String execute() {
		Object obj = super.show(JuniorCollegeStudentUnionWork.class, "JuniorCollegeStudentUnionWork");
		if (obj != null) {
			content = (JuniorCollegeStudentUnionWork) obj;
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
		return super.change(JuniorCollegeStudentUnionWork.class, content);
	}

	public JuniorCollegeStudentUnionWork getContent() {
		return content;
	}

	public void setContent(JuniorCollegeStudentUnionWork content) {
		this.content = content;
	}

}
