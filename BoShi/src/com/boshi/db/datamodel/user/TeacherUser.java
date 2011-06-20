package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;
import com.boshi.db.datamodel.genearchquestion.TheClass;

@NamedQueries( { @NamedQuery(name = "TeacherUser.ListAll", query = "select DISTINCT t from TeacherUser t order by t.name"),
		@NamedQuery(name = "TeacherUser.Amount", query = "select COUNT(DISTINCT t) from TeacherUser t") })
@Entity
@Table(name = "TeacherUser")
public class TeacherUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@OneToMany(mappedBy = "teacherUser", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<TheClass>		theClasses			= new LinkedList<TheClass>();

	public TeacherUser(String name, String password) {
		super(name, password, "teacher");
	}

	public TeacherUser() {
		super.setPower("teacher");
	}

	public List<TheClass> getTheClasses() {
		return theClasses;
	}

	public void setTheClasses(List<TheClass> theClasses) {
		this.theClasses = theClasses;
	}
}
