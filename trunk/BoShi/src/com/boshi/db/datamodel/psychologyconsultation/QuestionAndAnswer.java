package com.boshi.db.datamodel.psychologyconsultation;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import com.boshi.db.datamodel.user.StudentUser;

@NamedQueries( {
		@NamedQuery(name = "QuestionAndAnswer.ListAllTheStudent", query = "select DISTINCT t from QuestionAndAnswer t where t.studentUser.name=:name order by t.date DESC"),
		@NamedQuery(name = "QuestionAndAnswer.AmountTheStudent", query = "select COUNT(DISTINCT t) from QuestionAndAnswer t where t.studentUser.name=:name"),
		@NamedQuery(name = "QuestionAndAnswer.ListAllNotAnswer", query = "select DISTINCT t from QuestionAndAnswer t where t.haveAnswered=false order by t.date DESC"),
		@NamedQuery(name = "QuestionAndAnswer.AmountNotAnswer", query = "select COUNT(DISTINCT t) from QuestionAndAnswer t where t.haveAnswered=false") })
@Entity
@Table(name = "QuestionAndAnswer")
public class QuestionAndAnswer implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long				id;
	@Column(name = "Qcontent", length = 600)
	protected String			qContent			= null;
	@Column(name = "Acontent", length = 2048)
	protected String			aContent			= "《还没有回答》";
	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	protected Date				date				= new Date();
	@Column(name = "HaveAnswered")
	protected boolean			haveAnswered		= false;
	@ManyToOne(fetch = FetchType.EAGER)
	private StudentUser			studentUser			= null;

	public QuestionAndAnswer(String qContent, String aContent) {
		this.qContent = qContent;
		this.aContent = aContent;
	}

	public QuestionAndAnswer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudentUser getStudentUser() {
		return studentUser;
	}

	public void setStudentUser(StudentUser studentUser) {
		this.studentUser = studentUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getQContent() {
		return qContent;
	}

	public void setQContent(String content) {
		qContent = content;
	}

	public String getAContent() {
		return aContent;
	}

	public void setAContent(String content) {
		aContent = content;
	}

	public boolean isHaveAnswered() {
		return haveAnswered;
	}

	public void setHaveAnswered(boolean haveAnswered) {
		this.haveAnswered = haveAnswered;
	}
}
