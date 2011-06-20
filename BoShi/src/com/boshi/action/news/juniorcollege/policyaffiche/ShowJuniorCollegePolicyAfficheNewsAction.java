package com.boshi.action.news.juniorcollege.policyaffiche;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegePolicyAfficheNews;

public class ShowJuniorCollegePolicyAfficheNewsAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegePolicyAfficheNews	content				= new JuniorCollegePolicyAfficheNews();

	public String execute() {
		Object obj = super.show(JuniorCollegePolicyAfficheNews.class);
		if (obj != null) {
			content = (JuniorCollegePolicyAfficheNews) obj;
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
		return super.change(JuniorCollegePolicyAfficheNews.class, content);
	}

	public JuniorCollegePolicyAfficheNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegePolicyAfficheNews content) {
		this.content = content;
	}

}
