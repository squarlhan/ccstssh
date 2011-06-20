package com.boshi.action.news.juniorcollege.invitenews;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeInviteNews;

public class AddJuniorCollegeInviteNewsAction extends SuperAddNewsAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeInviteNews	content				= new JuniorCollegeInviteNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeInviteNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeInviteNews content) {
		this.content = content;
	}

}
