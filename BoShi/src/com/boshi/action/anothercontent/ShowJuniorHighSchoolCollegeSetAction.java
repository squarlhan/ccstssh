package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorHighSchoolCollegeSet;

public class ShowJuniorHighSchoolCollegeSetAction extends SuperShowAnotherContentAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorHighSchoolCollegeSet	content				= new JuniorHighSchoolCollegeSet();

	public String execute() {
		Object obj = super.show(JuniorHighSchoolCollegeSet.class, "JuniorHighSchoolCollegeSet");
		if (obj != null) {
			content = (JuniorHighSchoolCollegeSet) obj;
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
		return super.change(JuniorHighSchoolCollegeSet.class, content);
	}

	public JuniorHighSchoolCollegeSet getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolCollegeSet content) {
		this.content = content;
	}

}
