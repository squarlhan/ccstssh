package com.boshi.action.genearchquestion.genearch;

import java.util.*;
import com.boshi.action.SuperLoginAndRegisterAction;
import com.boshi.db.datamodel.user.GenearchUser;
import com.boshi.db.datamodel.genearchquestion.TheClass;

public class GenearchLoginAndRegisterAction extends SuperLoginAndRegisterAction {

	private static final long	serialVersionUID	= 1L;
	private GenearchUser		user				= new GenearchUser();
	private List<TheClass>		theClass			= new LinkedList<TheClass>();
	private String				whichClassName		= null;

	public String login() {
		return super.login(GenearchUser.class, user);
	}

	public String logout() {
		return super.logout();
	}

	public String prepareRegister() {
		try {
			theClass = (List<TheClass>) super.getDataCenterInterface().list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "toRegister";
	}

	public String register() {
		TheClass theClass = new TheClass();
		theClass.setName(whichClassName);
		user.setTheClass(theClass);
		try {
			super.getDataCenterInterface().createObject(user);
			return "registerSuccess";
		} catch (RuntimeException e) {
			super.addActionMessage(super.getText("register.error"));
		}
		return "registerError";
	}

	public List<TheClass> getTheClass() {
		return theClass;
	}

	public void setTheClass(List<TheClass> theClass) {
		this.theClass = theClass;
	}

	public String getWhichClassName() {
		return whichClassName;
	}

	public void setWhichClassName(String whichClassName) {
		this.whichClassName = whichClassName;
	}

	public GenearchUser getUser() {
		return user;
	}

	public void setUser(GenearchUser user) {
		this.user = user;
	}

}
