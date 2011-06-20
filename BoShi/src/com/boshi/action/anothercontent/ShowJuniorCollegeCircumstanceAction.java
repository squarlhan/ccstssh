package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeCircumstance;

public class ShowJuniorCollegeCircumstanceAction extends SuperShowAnotherContentAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorCollegeCircumstance	content				= new JuniorCollegeCircumstance();

	public String execute() {
		Object obj = super.show(JuniorCollegeCircumstance.class, "JuniorCollegeCircumstance");
		if (obj != null) {
			content = (JuniorCollegeCircumstance) obj;
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
		return super.change(JuniorCollegeCircumstance.class, content);
	}

	public JuniorCollegeCircumstance getContent() {
		return content;
	}

	public void setContent(JuniorCollegeCircumstance content) {
		this.content = content;
	}

}
