package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeSpecializedSet;

public class ShowJuniorCollegeSpecializedSetAction extends SuperShowAnotherContentAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorCollegeSpecializedSet	content				= new JuniorCollegeSpecializedSet();

	public String execute() {
		Object obj = super.show(JuniorCollegeSpecializedSet.class, "JuniorCollegeSpecializedSet");
		if (obj != null) {
			content = (JuniorCollegeSpecializedSet) obj;
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
		return super.change(JuniorCollegeSpecializedSet.class, content);
	}

	public JuniorCollegeSpecializedSet getContent() {
		return content;
	}

	public void setContent(JuniorCollegeSpecializedSet content) {
		this.content = content;
	}

}
