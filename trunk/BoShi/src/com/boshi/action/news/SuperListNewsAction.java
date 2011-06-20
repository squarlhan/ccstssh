package com.boshi.action.news;

import com.boshi.action.SuperListAction;
import com.boshi.db.datamodel.news.ContentOfNews;

public class SuperListNewsAction extends SuperListAction {
	private static final long	serialVersionUID	= 1L;

	public String remove(Class<?> classType) {
		String id = (String) super.getServletRequest().getParameter("id");
		try {
			if (id != null) {
				super.getDataCenterInterface().removeObject(classType, Long.valueOf(id));
			} else {
				for (Object obj : super.getResultList()) {
					ContentOfNews tmpContentOfNews = (ContentOfNews) obj;
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
}
