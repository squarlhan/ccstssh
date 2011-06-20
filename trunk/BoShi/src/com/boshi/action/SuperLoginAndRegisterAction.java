package com.boshi.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.boshi.db.datacenter.DataCenterInterface;
import com.boshi.db.datamodel.user.User;

public class SuperLoginAndRegisterAction extends ActionSupport implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;
	private HttpServletRequest	servletRequest		= null;
	private DataCenterInterface	dataCenterInterface	= null;

	public String login(Class<?> classType, User user) {
		try {
			User theUser = (User) dataCenterInterface.findObject(classType, user.getName());
			if (theUser != null && theUser.getPassword().equals(user.getPassword())) {
				servletRequest.getSession().setAttribute("name", theUser.getName());
				servletRequest.getSession().setAttribute("power", theUser.getPower());
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.addActionMessage(super.getText("login.error"));
		return "loginError";
	}

	public String logout() {
		servletRequest.getSession().invalidate();
//		servletRequest.getSession().setAttribute("name", null);
//		servletRequest.getSession().setAttribute("power", null);
		return "logout";
	}

	public String register(Object obj) {
		try {
			dataCenterInterface.createObject(obj);
			return "registerSuccess";
		} catch (Exception e) {
			super.addActionMessage(super.getText("register.error"));
		}
		return "registerError";
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
