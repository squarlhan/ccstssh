package com.boshi.action.news.juniorcollege.coalreport;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeCoalReport;

public class ShowCoalReportAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeCoalReport	content				= new JuniorCollegeCoalReport();

	public String execute() {
		Object obj = super.show(JuniorCollegeCoalReport.class);
		if (obj != null) {
			content = (JuniorCollegeCoalReport) obj;
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
		return super.change(JuniorCollegeCoalReport.class, content);
	}

	public JuniorCollegeCoalReport getContent() {
		return content;
	}

	public void setContent(JuniorCollegeCoalReport content) {
		this.content = content;
	}
}
