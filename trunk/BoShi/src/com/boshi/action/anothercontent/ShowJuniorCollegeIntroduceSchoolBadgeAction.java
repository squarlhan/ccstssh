package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeIntroduceSchoolBadge;

public class ShowJuniorCollegeIntroduceSchoolBadgeAction extends SuperShowAnotherContentAction {

	private static final long					serialVersionUID	= 1L;
	private JuniorCollegeIntroduceSchoolBadge	content				= new JuniorCollegeIntroduceSchoolBadge();

	public String execute() {
		Object obj = super.show(JuniorCollegeIntroduceSchoolBadge.class, "JuniorCollegeIntroduceSchoolBadge");
		if (obj != null) {
			content = (JuniorCollegeIntroduceSchoolBadge) obj;
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
		return super.change(JuniorCollegeIntroduceSchoolBadge.class, content);
	}

	public JuniorCollegeIntroduceSchoolBadge getContent() {
		return content;
	}

	public void setContent(JuniorCollegeIntroduceSchoolBadge content) {
		this.content = content;
	}

}
