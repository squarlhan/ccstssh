package com.boshi.action.schoolfellowrecord;

import com.boshi.action.SuperListAction;
import com.boshi.db.datamodel.schoolfellowrecord.RecordContent;

public class ListSchoolfellowRecordAction extends SuperListAction {
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
				super.getDataCenterInterface().removeObject(RecordContent.class, Long.valueOf(id));
			} else {
				for (Object obj : super.getResultList()) {
					RecordContent tmpRecordContent = (RecordContent) obj;
					if (tmpRecordContent.isHaveSelected() == true)
						super.getDataCenterInterface().removeObject(RecordContent.class, tmpRecordContent.getId());
				}
			}
			super.addActionMessage(super.getText("del.success"));
		} catch (Exception e) {
			super.addActionMessage(super.getText("del.error"));
		}
		return "del";
	}
}
