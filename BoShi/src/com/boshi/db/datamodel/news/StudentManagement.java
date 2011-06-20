package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "StudentManagement.ListAll", query = "select DISTINCT t from StudentManagement t order by t.date DESC"),
	@NamedQuery(name = "StudentManagement.Amount", query = "select COUNT(DISTINCT t) from StudentManagement t") })
@Entity
@Table(name = "CollegeNews")
public class StudentManagement extends ContentOfNews implements Serializable {

	// 学生管理
	private static final long	serialVersionUID	= 1L;

	public StudentManagement(String title, String content) {
		super(title, content);
	}

	public StudentManagement() {
	}
}
