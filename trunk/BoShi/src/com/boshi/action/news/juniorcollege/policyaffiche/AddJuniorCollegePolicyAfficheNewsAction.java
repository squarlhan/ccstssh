package com.boshi.action.news.juniorcollege.policyaffiche;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegePolicyAfficheNews;

public class AddJuniorCollegePolicyAfficheNewsAction extends SuperAddNewsAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegePolicyAfficheNews	content				= new JuniorCollegePolicyAfficheNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegePolicyAfficheNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegePolicyAfficheNews content) {
		this.content = content;
	}

}
