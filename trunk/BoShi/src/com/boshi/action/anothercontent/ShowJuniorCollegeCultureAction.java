package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeCulture;

public class ShowJuniorCollegeCultureAction extends SuperShowAnotherContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeCulture	content				= new JuniorCollegeCulture();

	public String execute() {
		Object obj = super.show(JuniorCollegeCulture.class, "JuniorCollegeCulture");
		if (obj != null) {
			content = (JuniorCollegeCulture) obj;
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
		return super.change(JuniorCollegeCulture.class, content);
	}

	public JuniorCollegeCulture getContent() {
		return content;
	}

	public void setContent(JuniorCollegeCulture content) {
		this.content = content;
	}

}
