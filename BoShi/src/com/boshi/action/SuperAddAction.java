package com.boshi.action;

import com.boshi.db.datacenter.DataCenterInterface;
import com.opensymphony.xwork2.ActionSupport;

public class SuperAddAction extends ActionSupport {

	private static final long	serialVersionUID	= 1L;
	private DataCenterInterface	dataCenterInterface	= null;

	public String add(Object content) {
		try {
			this.dataCenterInterface.createObject(content);
			super.addActionMessage(super.getText("add.success"));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			super.addActionMessage(super.getText("add.error"));

		}
		return INPUT;
	}

	public DataCenterInterface getDataCenterInterface() {
		return dataCenterInterface;
	}

	public void setDataCenterInterface(DataCenterInterface dataCenterInterface) {
		this.dataCenterInterface = dataCenterInterface;
	}

}
