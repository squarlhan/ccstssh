package com.boshi.action.news.juniorcollege.activitynotify;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeActivityNotifyNews;

public class AddJuniorCollegeActivityNotifyAction extends SuperAddNewsAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeActivityNotifyNews	content				= new JuniorCollegeActivityNotifyNews();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeActivityNotifyNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeActivityNotifyNews content) {
		this.content = content;
	}

}
