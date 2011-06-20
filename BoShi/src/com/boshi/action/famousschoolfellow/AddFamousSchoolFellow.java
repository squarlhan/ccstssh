package com.boshi.action.famousschoolfellow;

import com.boshi.action.SuperAddAction;
import com.boshi.db.datamodel.schoolfellowrecord.FamousSchoolFellow;

public class AddFamousSchoolFellow extends SuperAddAction {

	private static final long	serialVersionUID	= 1L;
	private FamousSchoolFellow			content				= new FamousSchoolFellow();
	
	public String execute() {
		return super.add(content);
	}

	public FamousSchoolFellow getContent() {
		return content;
	}

	public void setContent(FamousSchoolFellow content) {
		this.content = content;
	}
}
