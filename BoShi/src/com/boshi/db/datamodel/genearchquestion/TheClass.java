package com.boshi.db.datamodel.genearchquestion;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

import com.boshi.db.datamodel.user.TeacherUser;
import com.boshi.db.datamodel.user.GenearchUser;

@NamedQueries( { @NamedQuery(name = "TheClass.ListAll", query = "select DISTINCT t from TheClass t order by t.date DESC"),
		@NamedQuery(name = "TheClass.Amount", query = "select COUNT(DISTINCT t) from TheClass t") })
@Entity
@Table(name = "TheClass")
public class TheClass implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@Column(name = "Name", length = 128)
	protected String			name				= null;
	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	protected Date				date				= new Date();
	@OneToMany(mappedBy = "theClass", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<GenearchUser>	genearchUser		= new LinkedList<GenearchUser>();
	@ManyToOne(fetch = FetchType.EAGER)
	private TeacherUser			teacherUser			= null;
	@Transient
	protected boolean			haveSelected		= false;

	public TheClass(String name) {
		this.name = name;
	}

	public TheClass() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GenearchUser> getGenearchUser() {
		return genearchUser;
	}

	public void setGenearchUser(List<GenearchUser> genearchUser) {
		this.genearchUser = genearchUser;
	}

	public TeacherUser getTeacherUser() {
		return teacherUser;
	}

	public void setTeacherUser(TeacherUser teacherUser) {
		this.teacherUser = teacherUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isHaveSelected() {
		return haveSelected;
	}

	public void setHaveSelected(boolean haveSelected) {
		this.haveSelected = haveSelected;
	}
}
