package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.SeniorHighSchoolCollegeSet;

public class ShowSeniorHighSchoolCollegeSetAction extends SuperShowAnotherContentAction {

	private static final long			serialVersionUID	= 1L;
	private SeniorHighSchoolCollegeSet	content				= new SeniorHighSchoolCollegeSet();

	public String execute() {
		Object obj = super.show(SeniorHighSchoolCollegeSet.class, "SeniorHighSchoolCollegeSet");
		if (obj != null) {
			content = (SeniorHighSchoolCollegeSet) obj;
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
		return super.change(SeniorHighSchoolCollegeSet.class, content);
	}

	public SeniorHighSchoolCollegeSet getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolCollegeSet content) {
		this.content = content;
	}

}
