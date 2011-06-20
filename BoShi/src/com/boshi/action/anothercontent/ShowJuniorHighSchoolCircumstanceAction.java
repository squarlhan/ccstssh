package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorHighSchoolCircumstance;

public class ShowJuniorHighSchoolCircumstanceAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorHighSchoolCircumstance	content				= new JuniorHighSchoolCircumstance();

	public String execute() {
		Object obj = super.show(JuniorHighSchoolCircumstance.class, "JuniorHighSchoolCircumstance");
		if (obj != null) {
			content = (JuniorHighSchoolCircumstance) obj;
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
		return super.change(JuniorHighSchoolCircumstance.class, content);
	}

	public JuniorHighSchoolCircumstance getContent() {
		return content;
	}

	public void setContent(JuniorHighSchoolCircumstance content) {
		this.content = content;
	}

}
