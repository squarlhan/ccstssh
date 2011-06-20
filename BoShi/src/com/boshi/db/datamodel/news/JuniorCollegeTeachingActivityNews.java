package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeTeachingActivityNews.ListAll", query = "select DISTINCT t from JuniorCollegeTeachingActivityNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeTeachingActivityNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeTeachingActivityNews t") })
@Entity
@Table(name = "JuniorCollegeTeachingActivityNews")
public class JuniorCollegeTeachingActivityNews extends ContentOfNews implements Serializable {
	// 教学活动
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeTeachingActivityNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeTeachingActivityNews() {
	}
}
