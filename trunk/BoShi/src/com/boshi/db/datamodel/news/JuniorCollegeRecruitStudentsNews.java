package com.boshi.db.datamodel.news;

import javax.persistence.*;


import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorCollegeRecruitStudentsNews.ListAll", query = "select DISTINCT t from JuniorCollegeRecruitStudentsNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeRecruitStudentsNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeRecruitStudentsNews t") })
@Entity
@Table(name = "JuniorCollegeRecruitStudentsNews")
public class JuniorCollegeRecruitStudentsNews extends ContentOfNews implements Serializable {
//	大专招生新闻
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeRecruitStudentsNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeRecruitStudentsNews() {
	}
}
