package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorCStudentsWorks.ListAll", query = "select DISTINCT t from JuniorCStudentsWorks t order by t.date DESC"),
	@NamedQuery(name = "JuniorCStudentsWorks.Amount", query = "select COUNT(DISTINCT t) from JuniorCStudentsWorks t") })
@Entity
@Table(name = "JuniorCStudentsWorks")
public class JuniorCStudentsWorks extends ContentOfNews implements Serializable {

	// 学生会工作
	private static final long	serialVersionUID	= 1L;

	public JuniorCStudentsWorks(String title, String content) {
		super(title, content);
	}

	public JuniorCStudentsWorks() {
	}
}
