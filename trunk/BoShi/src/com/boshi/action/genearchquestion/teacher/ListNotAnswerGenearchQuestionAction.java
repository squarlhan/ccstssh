package com.boshi.action.genearchquestion.teacher;

import com.boshi.action.SuperListAction;
import com.boshi.db.datamodel.user.TeacherUser;
import com.boshi.db.datamodel.genearchquestion.TheClass;

public class ListNotAnswerGenearchQuestionAction extends SuperListAction {
	private static final long	serialVersionUID	= 1L;
	private TheClass			theClass			= new TheClass();

	public String execute() {
		String teacherName = (String) servletRequest.getSession().getAttribute("name");
		super.superExecute(new String[] { teacherName });
		return SUCCESS;
	}

	public String pagination() {
		String teacherName = (String) servletRequest.getSession().getAttribute("name");
		super.superPagination(new String[] { teacherName });
		return SUCCESS;
	}

	public String addClass() {
		try {
			String teacherName = (String) servletRequest.getSession().getAttribute("name");
			TeacherUser teacher = new TeacherUser();
			teacher.setName(teacherName);
			theClass.setTeacherUser(teacher);
			super.getDataCenterInterface().createObject(theClass);
			super.addActionMessage(super.getText("add.class.success"));
			return SUCCESS;
		} catch (RuntimeException e) {
			super.addActionMessage(super.getText("add.class.error"));
			e.printStackTrace();
		}
		return INPUT;
	}

	public TheClass getTheClass() {
		return theClass;
	}

	public void setTheClass(TheClass theClass) {
		this.theClass = theClass;
	}
}
