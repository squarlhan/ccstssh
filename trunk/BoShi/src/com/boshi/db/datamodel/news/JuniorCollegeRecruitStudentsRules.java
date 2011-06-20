package com.boshi.db.datamodel.news;

import java.io.Serializable;

import javax.persistence.*;


@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeRecruitStudentsRules.ListAll", query = "select DISTINCT t from JuniorCollegeRecruitStudentsRules t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeRecruitStudentsRules.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeRecruitStudentsRules t") })
@Entity
@Table(name = "JuniorCollegeRecruitStudentsRules")
public class JuniorCollegeRecruitStudentsRules extends ContentOfNews implements
		Serializable {
	// 大专招生简章
	private static final long serialVersionUID = 1L;

	public JuniorCollegeRecruitStudentsRules(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeRecruitStudentsRules() {
	}
}