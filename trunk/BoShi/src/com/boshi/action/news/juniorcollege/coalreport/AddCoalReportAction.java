package com.boshi.action.news.juniorcollege.coalreport;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeCoalReport;

public class AddCoalReportAction extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeCoalReport	content				= new JuniorCollegeCoalReport();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeCoalReport getContent() {
		return content;
	}

	public void setContent(JuniorCollegeCoalReport content) {
		this.content = content;
	}
}
