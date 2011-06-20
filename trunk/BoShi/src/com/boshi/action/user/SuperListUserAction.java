package com.boshi.action.user;

import com.boshi.action.SuperListAction;
import com.boshi.db.datamodel.user.User;

public class SuperListUserAction extends SuperListAction {

	private static final long	serialVersionUID	= 1L;

	public String remove(Class<?> classType) {
		String id = (String) super.getServletRequest().getParameter("id");
		try {
			if (id != null) {
				super.getDataCenterInterface().removeObject(classType, id);
			} else {
				for (Object obj : super.getResultList()) {
					User tmpUser = (User) obj;
					if (tmpUser.isHaveSelected() == true)
						super.getDataCenterInterface().removeObject(classType, tmpUser.getName());
				}
			}
			super.addActionMessage(super.getText("del.success"));
		} catch (Exception e) {
			super.addActionMessage(super.getText("del.crror"));
		}
		return "del";
	}
}
