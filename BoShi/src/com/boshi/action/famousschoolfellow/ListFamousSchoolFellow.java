package com.boshi.action.famousschoolfellow;

import com.boshi.action.SuperListAction;
import com.boshi.db.datamodel.schoolfellowrecord.FamousSchoolFellow;

public class ListFamousSchoolFellow extends SuperListAction {

	private static final long	serialVersionUID	= 1L;

	
	public String execute() {
		super.superExecute(null);
		return SUCCESS;
	}
	public String remove(Class<FamousSchoolFellow> classType) {
		String id = (String) super.getServletRequest().getParameter("id");
		try {
			if (id != null) {
				super.getDataCenterInterface().removeObject(classType, Long.valueOf(id));
			} else {
				for (Object obj : super.getResultList()) {
					FamousSchoolFellow tmpContentOfNews = (FamousSchoolFellow) obj;
					if (tmpContentOfNews.isHaveSelected() == true)
						super.getDataCenterInterface().removeObject(classType, tmpContentOfNews.getId());
				}
			}
			super.addActionMessage(super.getText("del.success"));
		} catch (Exception e) {
			super.addActionMessage(super.getText("del.error"));
		}
		return "del";
	}
	
	public String pagination() {
		super.superPagination(null);
		return SUCCESS;
	}

	
	
}
