package com.boshi.db.datamodel.news;

import javax.persistence.*;


import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "SeniorHighSchoolNews.ListAll", query = "select DISTINCT t from SeniorHighSchoolNews t order by t.date DESC"),
		@NamedQuery(name = "SeniorHighSchoolNews.Amount", query = "select COUNT(DISTINCT t) from SeniorHighSchoolNews t") })
@Entity
@Table(name = "SeniorHighSchoolNews")
public class SeniorHighSchoolNews extends ContentOfNews implements Serializable {
//	高中新闻
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolNews(String title, String content) {
		super(title, content);
	}

	public SeniorHighSchoolNews() {
	}
}
