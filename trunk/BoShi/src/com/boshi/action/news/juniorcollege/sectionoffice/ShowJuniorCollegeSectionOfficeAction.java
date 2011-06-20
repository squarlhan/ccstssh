package com.boshi.action.news.juniorcollege.sectionoffice;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeSectionOffice;

public class ShowJuniorCollegeSectionOfficeAction extends SuperShowNewsContentAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorCollegeSectionOffice	content				= new JuniorCollegeSectionOffice();

	public String execute() {
		Object obj = super.show(JuniorCollegeSectionOffice.class);
		if (obj != null) {
			content = (JuniorCollegeSectionOffice) obj;
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
		return super.change(JuniorCollegeSectionOffice.class, content);
	}

	public JuniorCollegeSectionOffice getContent() {
		return content;
	}

	public void setContent(JuniorCollegeSectionOffice content) {
		this.content = content;
	}

}
