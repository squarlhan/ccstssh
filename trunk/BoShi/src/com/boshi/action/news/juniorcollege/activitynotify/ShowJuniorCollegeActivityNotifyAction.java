package com.boshi.action.news.juniorcollege.activitynotify;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeActivityNotifyNews;

public class ShowJuniorCollegeActivityNotifyAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeActivityNotifyNews	content				= new JuniorCollegeActivityNotifyNews();

	public String execute() {
		Object obj = super.show(JuniorCollegeActivityNotifyNews.class);
		if (obj != null) {
			content = (JuniorCollegeActivityNotifyNews) obj;
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
		return super.change(JuniorCollegeActivityNotifyNews.class, content);
	}

	public JuniorCollegeActivityNotifyNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeActivityNotifyNews content) {
		this.content = content;
	}

}
