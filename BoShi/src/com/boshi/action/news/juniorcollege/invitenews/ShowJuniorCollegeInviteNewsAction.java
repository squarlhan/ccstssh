package com.boshi.action.news.juniorcollege.invitenews;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeInviteNews;

public class ShowJuniorCollegeInviteNewsAction extends SuperShowNewsContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegeInviteNews	content				= new JuniorCollegeInviteNews();

	public String execute() {
		Object obj = super.show(JuniorCollegeInviteNews.class);
		if (obj != null) {
			content = (JuniorCollegeInviteNews) obj;
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
		return super.change(JuniorCollegeInviteNews.class, content);
	}

	public JuniorCollegeInviteNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeInviteNews content) {
		this.content = content;
	}

}
