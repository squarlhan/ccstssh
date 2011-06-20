package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeIncumbentLeader;

public class ShowJuniorCollegeIncumbentLeaderAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeIncumbentLeader	content				= new JuniorCollegeIncumbentLeader();

	public String execute() {
		Object obj = super.show(JuniorCollegeIncumbentLeader.class, "JuniorCollegeIncumbentLeader");
		if (obj != null) {
			content = (JuniorCollegeIncumbentLeader) obj;
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
		return super.change(JuniorCollegeIncumbentLeader.class, content);
	}

	public JuniorCollegeIncumbentLeader getContent() {
		return content;
	}

	public void setContent(JuniorCollegeIncumbentLeader content) {
		this.content = content;
	}

}
