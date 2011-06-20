package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeCollegeSet;

public class ShowJuniorCollegeCollegeSetAction extends SuperShowAnotherContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeCollegeSet	content				= new JuniorCollegeCollegeSet();

	public String execute() {
		Object obj = super.show(JuniorCollegeCollegeSet.class, "JuniorCollegeCollegeSet");
		if (obj != null) {
			content = (JuniorCollegeCollegeSet) obj;
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
		return super.change(JuniorCollegeCollegeSet.class, content);
	}

	public JuniorCollegeCollegeSet getContent() {
		return content;
	}

	public void setContent(JuniorCollegeCollegeSet content) {
		this.content = content;
	}

}
