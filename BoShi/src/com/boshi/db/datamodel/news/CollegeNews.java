package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "CollegeNews.ListAll", query = "select DISTINCT t from CollegeNews t order by t.date DESC"),
		@NamedQuery(name = "CollegeNews.Amount", query = "select COUNT(DISTINCT t) from CollegeNews t") })
@Entity
@Table(name = "CollegeNews")
public class CollegeNews extends ContentOfNews implements Serializable {
	// 学院新闻
	private static final long	serialVersionUID	= 1L;

	public CollegeNews(String title, String content) {
		super(title, content);
	}

	public CollegeNews() {
	}
}
