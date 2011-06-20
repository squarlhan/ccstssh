package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeEntironmentManage;

public class ShowJuniorCollegeEntironmentManageAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeEntironmentManage	content				= new JuniorCollegeEntironmentManage();

	public String execute() {
		Object obj = super.show(JuniorCollegeEntironmentManage.class, "JuniorCollegeEntironmentManage");
		if (obj != null) {
			content = (JuniorCollegeEntironmentManage) obj;
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
		return super.change(JuniorCollegeEntironmentManage.class, content);
	}

	public JuniorCollegeEntironmentManage getContent() {
		return content;
	}

	public void setContent(JuniorCollegeEntironmentManage content) {
		this.content = content;
	}

}
