package com.boshi.db.datamodel.news;

import javax.persistence.*;


import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorHighSchoolNews.ListAll", query = "select DISTINCT t from JuniorHighSchoolNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorHighSchoolNews.Amount", query = "select COUNT(DISTINCT t) from JuniorHighSchoolNews t") })
@Entity
@Table(name = "JuniorHighSchoolNews")
public class JuniorHighSchoolNews extends ContentOfNews implements Serializable {
//	初中新闻
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolNews(String title, String content) {
		super(title, content);
	}

	public JuniorHighSchoolNews() {
	}
}
