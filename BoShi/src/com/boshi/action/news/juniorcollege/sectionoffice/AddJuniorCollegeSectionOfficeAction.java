package com.boshi.action.news.juniorcollege.sectionoffice;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeSectionOffice;

public class AddJuniorCollegeSectionOfficeAction extends SuperAddNewsAction {

	private static final long			serialVersionUID	= 1L;
	private JuniorCollegeSectionOffice	content				= new JuniorCollegeSectionOffice();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegeSectionOffice getContent() {
		return content;
	}

	public void setContent(JuniorCollegeSectionOffice content) {
		this.content = content;
	}

}
