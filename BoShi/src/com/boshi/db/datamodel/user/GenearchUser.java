package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;
import com.boshi.db.datamodel.genearchquestion.TheClass;
import com.boshi.db.datamodel.genearchquestion.GenearchQuestion;

@NamedQueries( { @NamedQuery(name = "GenearchUser.ListAll", query = "select DISTINCT t from GenearchUser t order by t.name"),
	@NamedQuery(name = "GenearchUser.Amount", query = "select COUNT(DISTINCT t) from GenearchUser t") })
@Entity
@Table(name = "GenearchUser")
public class GenearchUser extends User implements Serializable {
	private static final long		serialVersionUID	= 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	private TheClass				theClass			= null;
	@OneToMany(mappedBy = "genearchUser", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<GenearchQuestion>	genearchQuestion	= new LinkedList<GenearchQuestion>();

	public GenearchUser(String name, String password) {
		super(name, password, "genearch");
	}

	public GenearchUser() {
		super.setPower("genearch");
	}

	public TheClass getTheClass() {
		return theClass;
	}

	public void setTheClass(TheClass theClass) {
		this.theClass = theClass;
	}

	public List<GenearchQuestion> getGenearchQuestion() {
		return genearchQuestion;
	}

	public void setGenearchQuestion(List<GenearchQuestion> genearchQuestion) {
		this.genearchQuestion = genearchQuestion;
	}
}
