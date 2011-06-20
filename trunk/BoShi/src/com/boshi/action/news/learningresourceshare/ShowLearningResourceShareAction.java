package com.boshi.action.news.learningresourceshare;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.LearningResourceShare;

public class ShowLearningResourceShareAction extends SuperShowNewsContentAction {

	private static final long		serialVersionUID	= 1L;
	private LearningResourceShare	content				= new LearningResourceShare();

	public String execute() {
		Object obj = super.show(LearningResourceShare.class);
		if (obj != null) {
			content = (LearningResourceShare) obj;
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
		return super.change(LearningResourceShare.class, content);
	}

	public LearningResourceShare getContent() {
		return content;
	}

	public void setContent(LearningResourceShare content) {
		this.content = content;
	}

}
