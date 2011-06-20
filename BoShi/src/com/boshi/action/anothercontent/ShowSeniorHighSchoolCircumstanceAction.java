package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.SeniorHighSchoolCircumstance;

public class ShowSeniorHighSchoolCircumstanceAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private SeniorHighSchoolCircumstance	content				= new SeniorHighSchoolCircumstance();

	public String execute() {
		Object obj = super.show(SeniorHighSchoolCircumstance.class, "SeniorHighSchoolCircumstance");
		if (obj != null) {
			content = (SeniorHighSchoolCircumstance) obj;
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
		return super.change(SeniorHighSchoolCircumstance.class, content);
	}

	public SeniorHighSchoolCircumstance getContent() {
		return content;
	}

	public void setContent(SeniorHighSchoolCircumstance content) {
		this.content = content;
	}

}
