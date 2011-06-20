package com.boshi.action.news.learningresourceshare;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.LearningResourceShare;

public class AddLearningResourceShareAction extends SuperAddNewsAction {

	private static final long		serialVersionUID	= 1L;
	private LearningResourceShare	content				= new LearningResourceShare();

	public String execute() {
		return super.add(content);
	}

	public LearningResourceShare getContent() {
		return content;
	}

	public void setContent(LearningResourceShare content) {
		this.content = content;
	}

}
