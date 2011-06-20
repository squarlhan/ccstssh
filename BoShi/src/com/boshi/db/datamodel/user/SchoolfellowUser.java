package com.boshi.db.datamodel.user;

import java.util.*;
import javax.persistence.*;

import java.io.Serializable;

import com.boshi.db.datamodel.schoolfellowrecord.RecordContent;

@NamedQueries( { @NamedQuery(name = "SchoolfellowUser.ListAll", query = "select DISTINCT t from SchoolfellowUser t order by t.name"),
		@NamedQuery(name = "SchoolfellowUser.Amount", query = "select COUNT(DISTINCT t) from SchoolfellowUser t") })
@Entity
@Table(name = "SchoolfellowUser")
public class SchoolfellowUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Column(name = "ChineseName", length = 8)
	private String				chineseName			= "匿名";
	@Column(name = "Grade", length = 16)
	private String				grade				= "匿名";
	@Column(name = "ClassName", length = 16)
	private String				className			= "匿名";
	@OneToMany(mappedBy = "schoolfellowUser", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<RecordContent>	recordContents		= new LinkedList<RecordContent>();

	public SchoolfellowUser(String name, String password) {
		super(name, password, "schoolfellowUser");
	}

	public SchoolfellowUser() {
		super.setPower("schoolfellowUser");
	}

	public List<RecordContent> getRecordContents() {
		return recordContents;
	}

	public void setRecordContents(List<RecordContent> recordContents) {
		this.recordContents = recordContents;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
