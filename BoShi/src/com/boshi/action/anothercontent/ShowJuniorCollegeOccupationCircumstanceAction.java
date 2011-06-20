package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeOccupationCircumstance;

public class ShowJuniorCollegeOccupationCircumstanceAction extends SuperShowAnotherContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeOccupationCircumstance	content				= new JuniorCollegeOccupationCircumstance();

	public String execute() {
		Object obj = super.show(JuniorCollegeOccupationCircumstance.class, "JuniorCollegeOccupationCircumstance");
		if (obj != null) {
			content = (JuniorCollegeOccupationCircumstance) obj;
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
		return super.change(JuniorCollegeOccupationCircumstance.class, content);
	}

	public JuniorCollegeOccupationCircumstance getContent() {
		return content;
	}

	public void setContent(JuniorCollegeOccupationCircumstance content) {
		this.content = content;
	}

}
