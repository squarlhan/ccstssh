package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeExternalCommunion;

public class ShowJuniorCollegeExternalCommunionAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeExternalCommunion	content				= new JuniorCollegeExternalCommunion();

	public String execute() {
		Object obj = super.show(JuniorCollegeExternalCommunion.class, "JuniorCollegeExternalCommunion");
		if (obj != null) {
			content = (JuniorCollegeExternalCommunion) obj;
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
		return super.change(JuniorCollegeExternalCommunion.class, content);
	}

	public JuniorCollegeExternalCommunion getContent() {
		return content;
	}

	public void setContent(JuniorCollegeExternalCommunion content) {
		this.content = content;
	}

}
