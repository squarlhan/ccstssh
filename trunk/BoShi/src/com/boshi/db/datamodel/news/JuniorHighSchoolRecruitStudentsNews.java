package com.boshi.db.datamodel.news;

import javax.persistence.*;


import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorHighSchoolRecruitStudentsNews.ListAll", query = "select DISTINCT t from JuniorHighSchoolRecruitStudentsNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorHighSchoolRecruitStudentsNews.Amount", query = "select COUNT(DISTINCT t) from JuniorHighSchoolRecruitStudentsNews t") })
@Entity
@Table(name = "JuniorHighSchoolRecruitStudentsNews")
public class JuniorHighSchoolRecruitStudentsNews extends ContentOfNews implements Serializable {
//	初中招生新闻
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolRecruitStudentsNews(String title, String content) {
		super(title, content);
	}

	public JuniorHighSchoolRecruitStudentsNews() {
	}
}
