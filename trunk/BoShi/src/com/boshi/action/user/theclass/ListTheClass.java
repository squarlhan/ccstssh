package com.boshi.action.user.theclass;

import com.boshi.action.user.SuperListUserAction;
import com.boshi.db.datamodel.genearchquestion.TheClass;

public class ListTheClass extends SuperListUserAction {
	private static final long	serialVersionUID	= 1L;

	public String execute() {
		super.superExecute(null);
		return SUCCESS;
	}

	public String pagination() {
		super.superPagination(null);
		return SUCCESS;
	}

	public String remove() {
		String id = (String) super.getServletRequest().getParameter("id");
		try {
			if (id != null) {
				super.getDataCenterInterface().removeObject(TheClass.class, id);
			} else {
				for (Object obj : super.getResultList()) {
					TheClass theClass = (TheClass) obj;
					if (theClass.isHaveSelected() == true)
						super.getDataCenterInterface().removeObject(TheClass.class, theClass.getName());
				}
			}
			super.addActionMessage(super.getText("del.success"));
		} catch (Exception e) {
			super.addActionMessage(super.getText("del.crror"));
		}
		return "del";
	}
}
