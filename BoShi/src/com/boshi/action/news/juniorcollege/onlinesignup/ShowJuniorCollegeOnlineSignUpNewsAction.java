package com.boshi.action.news.juniorcollege.onlinesignup;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeOnlineSignUpNews;

public class ShowJuniorCollegeOnlineSignUpNewsAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeOnlineSignUpNews	content				= new JuniorCollegeOnlineSignUpNews();

	public String execute() {
		Object obj = super.show(JuniorCollegeOnlineSignUpNews.class);
		if (obj != null) {
			content = (JuniorCollegeOnlineSignUpNews) obj;
			return SUCCESS;
		}
		return ERROR;
	}

	public JuniorCollegeOnlineSignUpNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeOnlineSignUpNews content) {
		this.content = content;
	}

}
