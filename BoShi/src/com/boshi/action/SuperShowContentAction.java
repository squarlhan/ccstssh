package com.boshi.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datacenter.DataCenterInterface;
import com.opensymphony.xwork2.ActionSupport;

public class SuperShowContentAction extends ActionSupport implements ServletRequestAware {

	private static final long		serialVersionUID	= 1L;
	protected HttpServletRequest	servletRequest		= null;
	private DataCenterInterface		dataCenterInterface	= null;

	public Object show(Class<?> classType, Object id) {
		if (id != null)
			try {
				return dataCenterInterface.findObject(classType, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	public String change(Class<?> classType, Object content) {
		try {
			dataCenterInterface.changeContent(classType, content);
			super.addActionMessage(super.getText("change.success"));
			return "changeSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			super.addActionMessage(super.getText("change.error"));
		}
		return "changeError";
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public DataCenterInterface getDataCenterInterface() {
		return dataCenterInterface;
	}

	public void setDataCenterInterface(DataCenterInterface dataCenterInterface) {
		this.dataCenterInterface = dataCenterInterface;
	}

}
