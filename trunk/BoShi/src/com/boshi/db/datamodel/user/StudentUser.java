package com.boshi.db.datamodel.user;

import java.util.*;
import javax.persistence.*;

import java.io.Serializable;
import com.boshi.db.datamodel.psychologyconsultation.QuestionAndAnswer;

@NamedQueries( { @NamedQuery(name = "StudentUser.ListAll", query = "select DISTINCT t from StudentUser t order by t.name"),
		@NamedQuery(name = "StudentUser.Amount", query = "select COUNT(DISTINCT t) from StudentUser t") })
@Entity
@Table(name = "StudentUser")
public class StudentUser extends User implements Serializable {
	private static final long		serialVersionUID	= 1L;
	@OneToMany(mappedBy = "studentUser", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<QuestionAndAnswer>	questionAndAnswers	= new LinkedList<QuestionAndAnswer>();

	public StudentUser(String name, String password) {
		super(name, password, "student");
	}

	public StudentUser() {
		super.setPower("student");
	}

	public List<QuestionAndAnswer> getQuestionAndAnswers() {
		return questionAndAnswers;
	}

	public void setQuestionAndAnswers(List<QuestionAndAnswer> questionAndAnswers) {
		this.questionAndAnswers = questionAndAnswers;
	}
}
