package com.boshi.db.datamodel.genearchquestion;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import com.boshi.db.datamodel.user.GenearchUser;

@NamedQueries( {
		@NamedQuery(name = "GenearchQuestion.ListAllNotAnswer", query = "select DISTINCT t from GenearchQuestion t where t.genearchUser.theClass.teacherUser.name=:name AND t.haveAnswered=false order by t.date DESC"),
		@NamedQuery(name = "GenearchQuestion.AmountAllNotAnswer", query = "select COUNT(DISTINCT t) from GenearchQuestion t where t.genearchUser.theClass.teacherUser.name=:name AND t.haveAnswered=false"),
		@NamedQuery(name = "GenearchQuestion.ListAllTheGenearch", query = "select DISTINCT t from GenearchQuestion t where t.genearchUser.name=:name order by t.date DESC"),
		@NamedQuery(name = "GenearchQuestion.AmountTheGenearch", query = "select COUNT(DISTINCT t) from GenearchQuestion t where t.genearchUser.name=:name") })
@Entity
@Table(name = "GenearchQuestion")
public class GenearchQuestion implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long				id;
	@Column(name = "Qcontent", length = 2048)
	protected String			qContent			= null;
	@Column(name = "Acontent", length = 2048)
	protected String			aContent			= "《还没有回答》";
	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	protected Date				date				= new Date();
	@Column(name = "HaveAnswered")
	protected boolean			haveAnswered		= false;
	@ManyToOne(fetch = FetchType.EAGER)
	private GenearchUser		genearchUser		= null;

	public GenearchQuestion(String qContent, String aContent) {
		this.qContent = qContent;
		this.aContent = aContent;
	}

	public GenearchQuestion() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public GenearchUser getGenearchUser() {
		return genearchUser;
	}

	public void setGenearchUser(GenearchUser genearchUser) {
		this.genearchUser = genearchUser;
	}

	public boolean isHaveAnswered() {
		return haveAnswered;
	}

	public void setHaveAnswered(boolean haveAnswered) {
		this.haveAnswered = haveAnswered;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
